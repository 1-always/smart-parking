CREATE TABLE parking_spot (
  id UUID PRIMARY KEY,
  floor_id VARCHAR(20),
  spot_number VARCHAR(10),
  spot_type VARCHAR(10),
  is_occupied BOOLEAN
);

CREATE TABLE parking_ticket (
  id UUID PRIMARY KEY,
  ticket_number VARCHAR(50),
  vehicle_id UUID,
  spot_id UUID,
  entry_time TIMESTAMP,
  exit_time TIMESTAMP,
  fee_cents BIGINT
);

CREATE TABLE fee_policy (
  id BIGSERIAL PRIMARY KEY,
  base_minutes INT,
  base_amount_cents BIGINT,
  hourly_amount_cents BIGINT,
  rounding_minutes INT
);
