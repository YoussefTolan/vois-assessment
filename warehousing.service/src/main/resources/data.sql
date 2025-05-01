CREATE EXTENSION IF NOT EXISTS "pgcrypto";

INSERT INTO devices (
  id,
  pin,
  status,
  temperature,
  created_at,
  updated_at
)
SELECT
  gen_random_uuid(),
  lpad((floor(random() * 10000000)::int)::text, 7, '0'),
  CASE WHEN gs <= 50 THEN 'READY' ELSE 'ACTIVE' END,
  CASE WHEN gs <= 50 THEN -1 ELSE floor(random() * 11)::int END,
  now(),
  now()
FROM generate_series(1, 100) AS gs;
