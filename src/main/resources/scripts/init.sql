CREATE TABLE IF NOT EXISTS users (
     uuid TEXT PRIMARY KEY,
     name TEXT NOT NULL,
     email TEXT NOT NULL UNIQUE,
     password TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS products (
    uuid TEXT PRIMARY KEY,
    name TEXT NOT NULL,
    price REAL NOT NULL,
    quantity INTEGER NOT NULL
);

CREATE TABLE IF NOT EXISTS sales (
     uuid TEXT PRIMARY KEY,
     user_id TEXT NOT NULL,
     total REAL NOT NULL,
     payment_method TEXT NOT NULL,
     created_at TEXT NOT NULL,
     FOREIGN KEY (user_id) REFERENCES users(uuid)
);

CREATE TABLE IF NOT EXISTS sales_products (
      sale_id TEXT NOT NULL,
      product_id TEXT NOT NULL,
      FOREIGN KEY (sale_id) REFERENCES sales(uuid),
      FOREIGN KEY (product_id) REFERENCES products(uuid)
);