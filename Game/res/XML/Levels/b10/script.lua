print("Loading Lua for b10 level")

local z = luajava.newInstance("com.IB.SL.entity.TagEntity", "/XML/Entities/TestZombie.xml", false);
level:add(z);

while level:runningLua() do
--print("Pressing X: "..key:jump);
--print("Player X: "..pc:x());
--print("Player Y: "..level:getClientPlayer():y())
end
--print("Players: "..level:players:size());