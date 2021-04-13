-- Database name should be your Unity ID
USE jhnguye4; -- Change this

CREATE TABLE Store (
  storeID INT(9) NOT NULL,
  managerID INT (9) NOT NULL,
  address VARCHAR(128) NOT NULL,
  phone VARCHAR(16) NOT NULL,
  PRIMARY KEY(storeID, managerID)
);

CREATE TABLE Staff (
  staffID INT (9) NOT NULL,
  storeID INT (9) NOT NULL,
  name VARCHAR(128) NOT NULL,
  age INT NOT NULL,
  address VARCHAR (255) NOT NULL,
  title VARCHAR(128) NOT NULL,
  phone VARCHAR(16) NOT NULL,
  email VARCHAR(128) NOT NULL,
  employmentTime DATE NOT NULL,
  UNIQUE(email),
  FOREIGN KEY (storeID) REFERENCES Store(storeID),
  PRIMARY KEY(staffID),
  CHECK(age<=100)
);

CREATE TABLE SignUp (
  storeID INT (9) NOT NULL,
  signUpDate DATE NOT NULL,
  staffID INT (9) NOT NULL,
  memberID INT(9) NOT NULL,
  FOREIGN KEY(storeID) REFERENCES Store(storeID),
  FOREIGN KEY(staffID) REFERENCES Staff(staffID),
  PRIMARY KEY(memberID, storeID, staffID)
);

CREATE TABLE Member (
  memberID INT(9) NOT NULL,
  firstName VARCHAR(64) NOT NULL,
  lastName VARCHAR(64) NOT NULL,
  level VARCHAR(16) NOT NULL,
  email VARCHAR(128),
  phone VARCHAR(16),
  address VARCHAR(255) NOT NULL,
  activeStatus BOOL NOT NULL,
  rewardAmount DOUBLE(9, 2) DEFAULT 0 NOT NULL,
  UNIQUE(email),
  FOREIGN KEY(memberID) REFERENCES SignUp(memberID),
  PRIMARY KEY(memberID)
);

CREATE TABLE Supplier (
  supplierID INT(9) NOT NULL,
  name VARCHAR(128) NOT NULL,
  phone VARCHAR(16) NOT NULL,
  email VARCHAR(128) NOT NULL,
  location VARCHAR(128) NOT NULL,
  amountOwed DOUBLE(12,2) DEFAULT 0 NOT NULL,
  PRIMARY KEY(supplierID)
);

CREATE TABLE Merchandise (
  productID INT(9) NOT NULL,
  storeID INT(9) NOT NULL,
  name VARCHAR(128) NOT NULL,
  quantity INT(9) NOT NULL,
  buyPrice DOUBLE(9,2) NOT NULL,
  marketPrice DOUBLE(9,2) NOT NULL,
  productionDate DATE NOT NULL,
  expiration DATE,
  supplierID INT(9) NOT NULL,
  PRIMARY KEY(productID, supplierID, storeID),
  FOREIGN KEY(supplierID) REFERENCES Supplier(supplierID),
  FOREIGN KEY(storeID) REFERENCES Store(storeID),
  CHECK(quantity >= 0),
  CHECK(buyPrice >= 0.00),
  CHECK(marketPrice>= 0.00)
);

CREATE TABLE Discount (
  discountID INT(9) NOT NULL,
  productID INT(9) NOT NULL,
  priceReduction DOUBLE(9,2) NOT NULL,
  startDate DATE NOT NULL,
  endDate DATE NOT NULL,
  FOREIGN KEY(productID) REFERENCES Merchandise(productID),
  PRIMARY KEY(discountID),
  CHECK(priceReduction >= 0.00),
  CHECK(endDate >= startDate)
);

CREATE TABLE Transaction (
  transactionID INT(9) NOT NULL,
  storeID INT(9) NOT NULL,
  memberID INT(9) NOT NULL,
  cashierID INT(9) NOT NULL,
  date DATE NOT NULL,
  productID INT(9) NOT NULL,
  price DOUBLE(9,2) NOT NULL,
  quantity INT(9) DEFAULT 1 NOT NULL,
  total DOUBLE(9,2) NOT NULL,
  FOREIGN KEY(cashierID) REFERENCES Staff(staffID),
  FOREIGN KEY(memberID) REFERENCES Member(memberID),
  FOREIGN KEY(storeID) REFERENCES Store(storeID),
  FOREIGN KEY(productID) REFERENCES Merchandise(productID),
  PRIMARY KEY(transactionID, productID)
);

INSERT INTO Store VALUES (
  2000, 1001, '100 Warehouse Blvd, NC', '919-555-1872'
), (
  2001, 1001, '2221, B Street, NC', '919-2222-123'
), (
  2002, 1002, '2222, C Street, NC', '919-2222-456'
);

INSERT INTO Staff VALUES (
  1001, 2001, 'John', 32, '1101, S Street, NC', 'Manager', '919-1111-123', 'john01@gmail.com', '2018-10-10'
), (
  1002, 2002, 'Alex', 42, '1102, T Street, NC', 'Manager', '919-1111-456', 'alex12@gmail.com', '2015-07-19'
), (
  1003, 2001, 'Mary', 28, '1103, U Street, NC', 'cashier', '919-1111-789', 'mary34@gmail.com', '2019-07-19'
);

INSERT INTO SignUp VALUES (
  2001, '2019-08-01', 1003, 5001
), (
  2001, '2018-01-01', 1003, 5002
);

INSERT INTO Member VALUES (
  5001, 'James', 'Smith', 'gold', 'James5001@gmail.com', '919-5555-123', '5500, E Street, NC', true, 0
), (
  5002, 'David', 'Smith', 'platinum', 'David5002@gmail.com', '919-5555-456', '5501 F Street, NC', true, 4.00
);

INSERT INTO Supplier VALUES (
  4001, 'A Food Wholesale', '919-4444-123', 'afood@gmail.com', '4401, A Street, NC', 2500.00
), (
  4002, 'US Foods', '919-4444-456', 'usfoods@gmail.com', '4402, G Street, NC', 2500.00
);

INSERT INTO Merchandise VALUES (
  3001, 2001, 'AAA Paper Towels', 100, 10.00, 20.00, '2020-01-01', '2025-01-01', 4001
), (
  3002, 2001, 'BBB Hand soap', 200, 5.00, 10.00, '2020-01-01', '2022-01-01', 4002
), (
  3001, 2002, 'AAA Paper Towels', 150, 10.00, 20.00, '2020-01-01', '2025-01-01', 4001
), (
  3002, 2002, 'BBB Hand soap', 0, 5.00, 10.00, '2020-01-01', '2022-01-01', 4002
), (
  3003, 2001, 'CCC Red Wine', 100, 15.00, 30.00, '2021-01-01', '2022-01-01', 4002
);

INSERT INTO Discount VALUES (
  7001, 3001, 20.00, '2020-01-01', '2021-05-01'
), (
  7002, 3003, 20.00, '2021-01-01', '2021-05-01'
);

INSERT INTO Transaction VALUES (
  6001, 2001, 5002, 1003, '2020-05-01', 3001, '20.00', 5, '80.00'
), (
  6001, 2001, 5002, 1003, '2020-05-01', 3002, '10.00', 2, '20.00'
), (
  6002, 2001, 5002, 1003, '2020-06-01', 3002, '10.00', 10, '100.00'
), (
  6003, 2001, 5001, 1003, '2020-07-01', 3001, '20.00', 10, '160.00'
);
