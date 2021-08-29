# Highest Profit

# Description
## Parse a csv file and print of how many rows of data are in the csv file. Then check if the profit value in each row is valid. If the row is not valid, then remove the row from the data. Print off how many valid rows are in the data. Convert the valid data into JSON format and write it out to a file "data2.json". Order this data based on profit value and print the top 20 rows with highest profit values. 

# Challenges
## Since there was a lot of data to parse through, the most challenging part was making an efficient algorithm. I chose to use a merge sort algorithm because it is always O(n*Log n). I also came into an issue if there were rows in the csv with an added comma so I had to go back and check if there was a double quotation enclosed comma. If there was, I had to merge the two pieces around the comma to get the valid company. 
# Credits
## Merge sort algorithm supported by: https://www.geeksforgeeks.org/merge-sort/ 