CREATE TABLE devices (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    pin CHAR(7) UNIQUE NOT NULL,
    status VARCHAR(10) NOT NULL,
    temperature DOUBLE PRECISION NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT now(),
    updated_at TIMESTAMP NOT NULL DEFAULT now()
);

CREATE INDEX idx_devices_status_temp ON devices (status, temperature);
CREATE INDEX idx_devices_pin ON devices (pin);
CREATE INDEX idx_devices_created_at ON devices (created_at);
CREATE INDEX idx_devices_status_temp_pin ON devices (status, temperature, pin);


-- we can parition the db to have better performance based on status
-- CREATE TABLE devices_active PARTITION OF devices FOR VALUES IN ('ACTIVE');
-- CREATE TABLE devices_ready PARTITION OF devices FOR VALUES IN ('READY');