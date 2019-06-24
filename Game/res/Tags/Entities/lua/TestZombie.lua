print("Loading Lua for Spawn Haven level")

local z = luajava.newInstance("com.IB.SL.entity.mob.XML_Mob", 62, 50, "/XML/Entities/TestZombie.xml");
level:add(z);