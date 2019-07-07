print("Loading Lua for b20 level")

--local z = luajava.newInstance("com.IB.SL.entity.mob.XML_Mob", 62, 50, "/XML/Entities/TestZombie.xml");
--level:add(z);

function LoadB()
	level:MovePlayerTo(0, 0, "/Tags/Levels/b10", true);
end;