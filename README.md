# Investec Test

## Questions
 2.Given the attached addresses.json file which contains an array of address, do the following:

- a. Write a function to return a pretty print version of an address in the format: Type: Line details - city - province/state - postal code – country
 // example Java function definition
 public String prettyPrintAddress(Address address) {
     // your code here
 }
- b. Write a function to pretty print all the addresses in the attached file
- c. Write a function to print an address of a certain type (postal, physical, business)
- d. Write a function to validate an address
     - i.  A valid address must consist of a numeric postal code, a country, and at least one address line that is not blank or null.
     - ii. If the country is ZA, then a province is required as well.
- e. For each address in the attached file, determine if it is valid or not, and if not give an indication of what is invalid in a message format of your choice.