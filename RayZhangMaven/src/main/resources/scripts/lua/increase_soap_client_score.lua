-- ARGV[1] The url of soap client
-- ARGV[2] The expiration time of the rankings
local score = redis.call('ZINCRBY', KEYS[1] , 1, ARGV[1])
if redis.call('TTL', KEYS[1]) == -1 then
redis.call('EXPIRE', KEYS[1] , ARGV[2])
end
return score
