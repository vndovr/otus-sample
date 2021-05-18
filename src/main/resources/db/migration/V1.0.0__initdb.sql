--- common for all services ---
CREATE SEQUENCE HIBERNATE_SEQUENCE;

--- auth service tables ---
CREATE TABLE user_info (
  login VARCHAR(64) NOT NULL PRIMARY KEY,
  salt VARCHAR(8) NOT NULL,
  password VARCHAR(40) NOT NULL,
  roles VARCHAR(1024) NOT NULL
);

--- create default admin user / could not be created view registration api
INSERT INTO user_info (login, salt, password, roles) VALUES ('admin', '58368', 'b9079b8ec07d1a5e034fbd0dc3736b867a2a64f1', 'user,admin');

-- system account - no password, - just as a 
INSERT INTO user_info (login, salt, password, roles) VALUES ('system', '', '', '');

--- user profile service tables 
CREATE TABLE user_profile (
  userid VARCHAR(64) NOT NULL PRIMARY KEY,
  firstname VARCHAR(64) NOT NULL,
  lastname VARCHAR(64) NOT NULL,
  email VARCHAR(64) NOT NULL,
  city VARCHAR(64),
  postalcode VARCHAR(6),
  addressline VARCHAR(128),
  phone VARCHAR(16),
  version INTEGER NOT NULL DEFAULT 0
);

--- user account service tables
CREATE TABLE account (
  userid VARCHAR(64) NOT NULL PRIMARY KEY,
  amount DECIMAL(15,2) NOT NULL,
  version INTEGER NOT NULL DEFAULT 0
);

CREATE TABLE account_event (
  id VARCHAR(36) NOT NULL PRIMARY KEY,
  creditaccount VARCHAR(64) NOT NULL,
  debitaccount VARCHAR(64) NOT NULL,
  amount DECIMAL(15,2) NOT NULL,
  rolledbak BOOLEAN NOT NULL,
  createdat TIMESTAMP NOT NULL
);

--- create system account - for money charge from user
INSERT INTO account (userid, amount, version) VALUES ('system', 0, 0);
INSERT INTO account (userid, amount, version) VALUES ('admin', 0, 0);

--- event store
CREATE TABLE event (
  id SERIAL NOT NULL PRIMARY KEY,
  xreqid VARCHAR(36),
  externalid VARCHAR(36) NOT NULL,
  entity VARCHAR(128) NOT NULL,
  type VARCHAR(32) NOT NULL,
  data VARCHAR(4096),
  userid VARCHAR(64),
  creationdate TIMESTAMP NOT NULL
);

--- order service tables 

--- normalized structure for order with items 1:N
CREATE TABLE order_info (
  id VARCHAR(36) NOT NULL PRIMARY KEY,
  userid VARCHAR(64) NOT NULL,
  creationdate TIMESTAMP NOT NULL,
  description VARCHAR(1024),
  deliverytime TIMESTAMP,
  state VARCHAR(16) NOT NULL
);

CREATE TABLE order_item (
  orderid VARCHAR(36) NOT NULL,
  itemid VARCHAR(36) NOT NULL,
  itemname VARCHAR(128) NOT NULL,
  quantity INTEGER NOT NULL,
  price DECIMAL(15,2) NOT NULL,
  CONSTRAINT pk PRIMARY KEY (orderid, itemid),
  CONSTRAINT fk1 FOREIGN KEY (orderid) REFERENCES order_info (id) ON DELETE CASCADE
);

-- denormalized structure for order overview
CREATE TABLE order_overview (
  id VARCHAR(36) NOT NULL PRIMARY KEY,
  userid VARCHAR(64) NOT NULL,
  creationdate TIMESTAMP NOT NULL,
  description VARCHAR(1024),
  deliverytime TIMESTAMP,
  state VARCHAR(16) NOT NULL,
  price DECIMAL(15,2) NOT NULL
);

-- notification tables
CREATE TABLE notification (
  id SERIAL NOT NULL PRIMARY KEY,
  userid VARCHAR(36) NOT NULL,
  email VARCHAR(64) NOT NULL,
  subject VARCHAR(64) NOT NULL,
  body VARCHAR(1024) NOT NULL,
  createdat TIMESTAMP NOT NULL
);

-- transaction tables
CREATE TABLE transaction (
  id VARCHAR(36) NOT NULL PRIMARY KEY,
  state VARCHAR(16) NOT NULL,
  debitaccount VARCHAR(64) NOT NULL,
  creditaccount VARCHAR(64) NOT NULL,
  orderid VARCHAR(36) NOT NULL,
  amount DECIMAL(15,2) NOT NULL,
  event VARCHAR(4096) NOT NULL,
  createdat TIMESTAMP NOT NULL
);

-- delivery tables
CREATE TABLE delivery_event (
  id VARCHAR(36) NOT NULL PRIMARY KEY,
  createdat TIMESTAMP NOT NULL
);

CREATE TABLE delivery_counter (
  id DATE NOT NULL PRIMARY KEY,
  counter INTEGER NOT NULL,
  version INTEGER NOT NULL
);

-- warehouse tables
CREATE TABLE warehouse_event (
  id VARCHAR(36) NOT NULL PRIMARY KEY,
  createdat TIMESTAMP NOT NULL
);

CREATE TABLE warehouse_product (
  id VARCHAR(36) NOT NULL PRIMARY KEY,
  name VARCHAR(64) NOT NULL,
  available INTEGER NOT NULL,
  reserved INTEGER NOT NULL,
  version INTEGER NOT NULL
);

INSERT INTO warehouse_product (id, name, available, reserved, version) VALUES ('1', 'Spinning reel Shimano Exage 2500', 100, 0, 0);
INSERT INTO warehouse_product (id, name, available, reserved, version) VALUES ('2', 'Spinning rod Shimano Olivio 2.1m', 10, 0, 0);
INSERT INTO warehouse_product (id, name, available, reserved, version) VALUES ('3', 'Sprinnig rod Shimano Technium 2.7m', 100, 0, 0);
INSERT INTO warehouse_product (id, name, available, reserved, version) VALUES ('4', 'Spinning reel Shimano Baitrunner 4000', 10, 0, 0);
INSERT INTO warehouse_product (id, name, available, reserved, version) VALUES ('5', 'Trout fishing box Flambeau', 10, 0, 0);



