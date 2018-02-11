LIVE_OBJECTS = {}

-- Add object to the live object roster for that particular function
function addLiveFunction(obj,func_name,func)
  if obj then
    if not obj.index_table then
      obj.index_table = {}
    end

    if obj[func_name] then
      print('Overwriting '..func_name..' of object')
      print(obj)
      LIVE_OBJECTS[func_name][obj.index_table[func_name]] = nil
    end
    if not LIVE_OBJECTS[func_name] then
      LIVE_OBJECTS[func_name] = {}
    end
    obj[func_name] = func;
    LIVE_OBJECTS[func_name][#LIVE_OBJECTS[func_name] + 1] = obj

    obj.index_table[func_name] = #LIVE_OBJECTS[func_name]
    -- print('OBJECT ADDED AS LIVE_OBJECTS[' .. func_name .. ']' .. '[' .. obj.index_table[func_name] .. ']')

    return obj.index_table[func_name]
  end
  return nil
end

-- Nulls out function on the object. Also nulls out its entry in the live object table
function removeLiveFunction(obj,func_name)
  local id = obj.index_table[func_name]
  if not id then
    --print('ERR: Cannot delete object, does not have an id field for ' .. func_name)
    return
  end

  --print('DELETE LIVE_OBJECTS [\'' .. func_name .. '\']' .. '[' .. id .. ']')

  LIVE_OBJECTS[func_name][id] = false
  obj.index_table[func_name] = nil
  obj[func_name] = nil
end

-- Run all objects in the live objects table for that function
function runLiveFunction(func_name,arg)
  if not LIVE_OBJECTS[func_name] then
    return
  end

  for i,obj in ipairs(LIVE_OBJECTS[func_name]) do
    if obj and obj[func_name] then
      -- Pass in self as the first param
      -- Takes in one additional arg. Could be used as a table for multiple args

      if obj ~= PRIORITY_OBJECT then
        obj[func_name](obj,arg)
      end
    end
  end

  if PRIORITY_OBJECT then
    PRIORITY_OBJECT[func_name](PRIORITY_OBJECT,arg)
  end
end
