local val = redis.call('get', KEYS[1])
if val ~= nil then
    redis.call('del', KEYS[1])
end
return val