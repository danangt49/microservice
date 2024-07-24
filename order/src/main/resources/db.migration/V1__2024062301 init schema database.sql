CREATE TABLE orders
(
    order_id         INT AUTO_INCREMENT NOT NULL,
    created_at       datetime           NOT NULL,
    updated_at       datetime           NOT NULL,
    customer_id      VARCHAR(255)       NOT NULL,
    shipping_address TEXT               NULL,
    order_status     VARCHAR(255)       NULL,
    total_price      DECIMAL(10, 2)     NULL,
    invoice          TEXT               NULL,
    note             TEXT               NULL,
    CONSTRAINT pk_orders PRIMARY KEY (order_id)
);

CREATE TABLE order_details
(
    order_detail_id INT AUTO_INCREMENT NOT NULL,
    created_at      datetime           NOT NULL,
    updated_at      datetime           NOT NULL,
    order_id        INT                NOT NULL,
    product_id      INT                NOT NULL,
    quantity        INT                NULL,
    price           DECIMAL(10, 2)     NULL,
    CONSTRAINT pk_order_details PRIMARY KEY (order_detail_id)
);

ALTER TABLE order_details
    ADD CONSTRAINT FK_ORDER_DETAILS_ON_ORDER FOREIGN KEY (order_id) REFERENCES orders (order_id);