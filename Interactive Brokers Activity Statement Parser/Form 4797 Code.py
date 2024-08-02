from datetime import datetime
import pandas as pd

# File name and year 
file_path = 'F4193571_U1375769_2022 Activity.xlsx'
year = '2022'

# Read the file
df = pd.read_excel(file_path, header=0)
outer_trades = df[df['Statement'] == 'Trades']   # Trades data frame
outer_mark_to_mark = df[df['Statement'] == 'Mark-to-Market Performance Summary']  # Mark to market data frame
outer_corp_actions = df[df['Statement'] == 'Corporate Actions']

#Make inner trades data frame
inner_trades = outer_trades.iloc[0:, 1:16]
inner_trades.columns = inner_trades.iloc[0]  # Set the first row as the header
inner_trades = inner_trades[1:].reset_index(drop=True)
inner_trades.rename(columns=inner_trades.iloc[0])
inner_trades = inner_trades[inner_trades['Asset Category'] == 'Stocks']
inner_trades = inner_trades[0:].reset_index(drop=True)

#Make inner mark to market data frame
inner_mark_to_mark = outer_mark_to_mark.iloc[0:, 2:13]
inner_mark_to_mark.columns = inner_mark_to_mark.iloc[0]
inner_mark_to_mark = inner_mark_to_mark[1:].reset_index(drop=True)
inner_mark_to_mark.rename(columns=inner_mark_to_mark.iloc[0])
inner_mark_to_mark = inner_mark_to_mark[inner_mark_to_mark['Asset Category'] == 'Stocks']
inner_mark_to_mark = inner_mark_to_mark[0:].reset_index(drop=True)

#Make inner corporate actions data frame
inner_corp_actions = outer_corp_actions.iloc[0:, 0:11]
inner_corp_actions.columns = inner_corp_actions.iloc[0]
inner_corp_actions = inner_corp_actions[1:].reset_index(drop=True)
inner_corp_actions.rename(columns=inner_corp_actions.iloc[0])


# Dates for Mark to Market transactions
mark_open_date = year + "-01-01, 12:00:00"
mark_close_date = year + "-12-31, 12:00:00"


#Go through mark to market and insert the open/closes into Trades data frame
for mark_index, mark_row in inner_mark_to_mark.iterrows():

    if mark_row['Prior Quantity'] != 0:                 # Insert an open into Trades data frame

        inner_trades = inner_trades._append({
        'Symbol': mark_row['Symbol'],
        'Date/Time': mark_open_date,
        'Quantity': mark_row['Prior Quantity'],
        'T. Price': mark_row['Prior Price'],
        'Comm/Fee': 0,
        'Code': 'O'
        }, ignore_index=True)
        
    
    if mark_row['Current Quantity'] != 0:           # Insert a close into Trades data frame

        inner_trades = inner_trades._append({
        'Symbol': mark_row['Symbol'],
        'Date/Time': mark_close_date,
        'Quantity': (mark_row['Current Quantity'] * -1),
        'T. Price': mark_row['Current Price'],
        'Comm/Fee': 0,
        'Code': 'C'
        }, ignore_index=True)


# Resort the Trades data frame and reset index
inner_trades = inner_trades.sort_values(by=['Symbol', 'Date/Time'], ascending=True)
inner_trades = inner_trades[0:].reset_index(drop=True)


# Excel version of new Trades data frame after inserting
fname = "New-Trades-TY" + year + "-{:%Y-%m-%d %H-%M-%S}.xlsx".format(datetime.now())
inner_trades.to_excel(fname, index=False)


# Create a new dataframe to store the Corporate Actions output
corp_df = pd.DataFrame(columns=['Symbol', 'Description', 'Open Quantity', 'Corporate Quantity'])
        

used_trades_close_indexes = []
pairs = []

# Loop through trades section 
for index, row in inner_trades.iterrows():


    if 'O' in str(row['Code']) and row['Quantity'] != 0:              # Looking for opening

        open = True
        close = False
        curr_symbol = row['Symbol']
        abs_open_quantity = abs(row['Quantity'])

        sales_price = 0
          
        for close_index in range(index + 1, len(inner_trades)):             # Looking for closing
            close_row = inner_trades.iloc[close_index]

            if close_row['Symbol'] != curr_symbol:              # Moved on to other symbol so can end looking for matching close
                break

            if 'C' in str(close_row['Code']) and close_row['Symbol'] == curr_symbol and close_index not in used_trades_close_indexes:

                
                abs_close_quantity = abs(close_row['Quantity'])

                if abs_open_quantity == abs_close_quantity:                               # Found perfect close open match
                    close = True
                    close_tupple = (close_row['Date/Time'], (close_row['Quantity'] * -1 * close_row['T. Price']) + sales_price + close_row['Comm/Fee'])
                    pairs.append((row, close_tupple))
                    used_trades_close_indexes.append(close_index)
                    break
                
                elif abs_open_quantity < abs_close_quantity:                     # Close quantity bigger than open
                    close = True
                                                                                                                    # Take proportion of commision since only using some of close quantity
                    close_tupple = (close_row['Date/Time'], (row['Quantity'] * close_row['T. Price']) + sales_price + ((abs_open_quantity / abs_close_quantity) * close_row['Comm/Fee']))

                    inner_trades.at[close_index, 'Comm/Fee'] = close_row['Comm/Fee'] - ((abs_open_quantity / abs_close_quantity) * close_row['Comm/Fee'])  # Taking proportion out of commision

                    pairs.append((row, close_tupple))

                    if close_row['Quantity'] > 0:
                        inner_trades.at[close_index, 'Quantity'] = (close_row['Quantity'] - abs_open_quantity)

                    else:
                        inner_trades.at[close_index, 'Quantity'] = (close_row['Quantity'] + abs_open_quantity) 

                    break

                else:                                                   # Close quantity smaller than open
                    sales_price += (close_row['Quantity'] * -1 * close_row['T. Price'] + close_row['Comm/Fee'])
                    used_trades_close_indexes.append(close_index)
                    abs_open_quantity -= abs_close_quantity
                    

        if not close:                           # Could not find close, look in Corporate Actions

            found_in_corp = False

            if type(curr_symbol) == str:        # Valid Company name found (blank space would be float)

                for corp_index, corp_row in inner_corp_actions.iterrows():

                    if type(corp_row['Description']) == str:        # Valid Description found (blank description is float)

                        if curr_symbol in corp_row['Description']:   # Found Company in the Corp Actions
                            #print(curr_symbol + ": " + corp_row['Description'] + ":" + str(abs_open_quantity) + ":" + str(abs(corp_row['Quantity'])))

                            open_quant = abs_open_quantity

                            if row['Quantity'] < 0:             # Revert from absolute value to true value for the open
                                open_quant = open_quant * -1

                            corp_df = corp_df._append({
                            'Symbol': curr_symbol,
                            'Description': corp_row['Description'],
                            'Open Quantity': open_quant,
                            'Corporate Quantity': corp_row['Quantity']
                            }, ignore_index=True)

                            found_in_corp = True
            
                if not found_in_corp:

                    open_quant = abs_open_quantity

                    if row['Quantity'] < 0:             # Revert from absolute value to true value for the open
                        open_quant = open_quant * -1

                    print("Open Quantity " + curr_symbol + ": " + str(open_quant))


for trade_index, trade_row in inner_trades.iterrows():

    if 'C' in str(trade_row['Code']) and 'O' not in str(trade_row['Code']) and trade_index not in used_trades_close_indexes and type(trade_row['Symbol']) == str:  # Find valid closes that were not used
        print("Close Quantity " + trade_row['Symbol'] + ": " + str(trade_row['Quantity']))


# Excel version of Corporate Actions output
fname = "Corp-Actions-TY" + year + "-{:%Y-%m-%d %H-%M-%S}.xlsx".format(datetime.now())
corp_df.to_excel(fname, index=False)

# Create a new dataframe to store the pairs
paired_df = pd.DataFrame(columns=['Desc of Property', 'Date Acquired', 'Date Sold', 'Sales Price', 'Cost', 'Gain/Loss'])

# Fill the new dataframe with paired rows
for pair in pairs:
    open_row, close_row_tupple = pair

    date_sold, sales_price = close_row_tupple

    sales_price = round(sales_price, 2)

    quantity = open_row['Quantity']

    if quantity < 0:
        symbol = "{num} (S) {symb}".format(num = abs(open_row['Quantity']), symb = open_row['Symbol'])
    else:
        symbol = "{num} (L) {symb}".format(num = abs(open_row['Quantity']), symb = open_row['Symbol'])

    date_acquired = open_row['Date/Time']

    cost = (quantity * -1 * open_row['T. Price']) + open_row['Comm/Fee']
    
    cost = round(cost, 2)

    paired_df = paired_df._append({
        'Desc of Property': symbol,
        'Date Acquired': date_acquired,
        'Date Sold': date_sold,
        'Sales Price': sales_price,
        'Cost': cost,
        'Gain/Loss': sales_price + cost
    }, ignore_index=True)

# Save the paired dataframe to a new Excel file
fname = "Form-4797-TY" + year + "-{:%Y-%m-%d %H-%M-%S}.xlsx".format(datetime.now())
paired_df.to_excel(fname, index=False)

print("Process Completed")

