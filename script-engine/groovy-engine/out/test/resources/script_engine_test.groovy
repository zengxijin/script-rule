/**
 * @author xijin.zeng created on 2018/10/18
 */

def database = ['key1': '{"hair":"black", "eyes": "blue"}',
                'key2': '{"hair":"black", "eyes":"grey"}']

def keyVal = binding.getVariable("key")
def result = ""

result = database.get(keyVal)
result
