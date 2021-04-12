CREATE DATABASE wolfwr;
USE wolfwr;

CREATE TABLE Staff (
  staffID INT (9) NOT NULL,
  name VARCHAR(128) NOT NULL,
  age INT NOT NULL,
  address VARCHAR (255) NOT NULL,
  title VARCHAR(128) NOT NULL,
  phone VARCHAR(16) NOT NULL,
  email VARCHAR(128) NOT NULL,
  employmentTime INT(2) DEFAULT 1 NOT NULL,
  UNIQUE(email),
  PRIMARY KEY(staffID),
  CHECK(age<=100)
);

CREATE TABLE Store (
  storeID INT(9) NOT NULL,
  managerID INT (9) NOT NULL,
  phone VARCHAR(16) NOT NULL,
  address VARCHAR(128) NOT NULL,
  FOREIGN KEY(managerID) REFERENCES Staff(staffID),
  PRIMARY KEY(storeID, managerID)
);

CREATE TABLE SignUp (
  memberID INT(9) NOT NULL,
  storeID INT (9) NOT NULL,
  signUpDate DATE NOT NULL,
  staffID (9) NOT NULL,
  FOREIGN KEY(storeID) REFERENCES Store(storeID),
  FOREIGN KEY(staffID) REFERENCES Staff(staffID),
  PRIMARY KEY(memberID, storeID, staffID)
);

CREATE TABLE Member (
  memberID INT(9) NOT NULL,
  level VARCHAR(16) NOT NULL,
  email VARCHAR(128),
  firstName VARCHAR(64) NOT NULL,
  lastName VARCHAR(64) NOT NULL,
  phone VARCHAR(16),
  address VARCHAR(255) NOT NULL,
  rewardAmount DOUBLE(9, 2) DEFAULT 0 NOT NULL,
  activeStatus BOOL NOT NULL,
  UNIQUE(email),
  FOREIGN KEY(memberID) REFERENCES SignUp(memberID),
  PRIMARY KEY(memberID)
);

CREATE TABLE Supplier (
  supplierID INT(9) NOT NULL,
  name VARCHAR(128) NOT NULL,
  phone VARCHAR(16) NOT NULL,
  location VARCHAR(128) NOT NULL,
  amountOwed DOUBLE(12,2) DEFAULT 0 NOT NULL,
  email VARCHAR(128) NOT NULL,
  PRIMARY KEY(supplierID)
);

CREATE TABLE Merchandise (
  productID INT(9) NOT NULL,
  storeID INT(9) NOT NULL,
  name VARCHAR(128) NOT NULL,
  quantity INT(9) NOT NULL,
  buyPrice DOUBLE(9,2) NOT NULL,
  marketPrice DOUBLE(9,2) NOT NULL,
  supplierID INT(9) NOT NULL,
  expiration DATE,
  productionDate DATE NOT NULL,
  PRIMARY KEY(productID, supplierID, storeID),
  FOREIGN KEY(supplierID) REFERENCES Supplier(supplierID),
  FOREIGN KEY(storeID) REFERENCES Store(storeID),
  CHECK(quantity >= 0),
  CHECK(buyPrice >= 0.00),
  CHECK(marketPrice>= 0.00)
);

CREATE TABLE Discount (
  productID INT(9) NOT NULL,
  startDate DATE NOT NULL,
  endDate DATE NOT NULL,
  priceReduction DOUBLE(9,2) NOT NULL,
  FOREIGN KEY(productID) REFERENCES Merchandise(productID),
  PRIMARY KEY(productID, startDate, endDate),
  CHECK(priceReduction >= 0.00),
  CHECK(endDate >= startDate)
);

CREATE TABLE Transaction (
  transactionID INT(9) NOT NULL,
  total DOUBLE(9,2) NOT NULL,
  productID INT(9) NOT NULL,
  date DATE NOT NULL,
  cashierID INT(9) NOT NULL,
  memberID INT(9) NOT NULL,
  storeID INT(9) NOT NULL,
  quantity INT(9) DEFAULT 1 NOT NULL,
  FOREIGN KEY(cashierID) REFERENCES Staff(staffID),
  FOREIGN KEY(memberID) REFERENCES Member(memberID),
  FOREIGN KEY(storeID) REFERENCES Store(storeID),
  FOREIGN KEY(productID) REFERENCES Merchandise(productID),
  PRIMARY KEY(transactionID, productID)
);
