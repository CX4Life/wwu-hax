LIVE_OBJECTS = {}

function addLiveFunction(obj,func_name,func)
  if obj then
    if obj[func_name] then
      print('Overwriting '..func_name..' of object')
      print(obj)
    end
    if not LIVE_OBJECTS[func_name] then
      LIVE_OBJECTS[func_name] = {}
    end
    obj[func_name] = func;
    print(func_name)
    print(func)
    print(unpack(obj))
    print(obj['draw'])
    LIVE_OBJECTS[func_name][#LIVE_OBJECTS[func_name] + 1] = obj
  end
end

-- LIVE_OBJECTS['draw'][1]()
