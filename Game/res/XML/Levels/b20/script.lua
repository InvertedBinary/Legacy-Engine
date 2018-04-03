print("Loading Lua for a10 level")

local z = luajava.newInstance("com.IB.SL.entity.mob.XML_Mob", 62, 50, "/XML/Entities/TestZombie.xml");
level:add(z);

while level:runningLua() do
--print("Pressing X: "..key:jump);
--print("Player X: "..pc:x());
--print("Player Y: "..level:getClientPlayer():y())
end
--print("Players: "..level:players:size());