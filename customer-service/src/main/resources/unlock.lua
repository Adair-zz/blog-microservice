---
--- Generated by EmmyLua(https://github.com/EmmyLua)
--- Created by 张峥.
--- DateTime: 2024/1/24 1:18
---
if (redis.call('get', KEYS[1]) == ARGV[1]) then
    return redis.call('del', KEYS[1])
end
return 0