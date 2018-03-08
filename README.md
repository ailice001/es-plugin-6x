* 环境依赖：
    * elasticsearch >= 5.x
    * pom中对不同elasticsearch版本的依赖，最终插件只能应用在对应版本的elasticsearch上

* 编译安装：
    * 编译：mvn clean package -DskipTests
    * 安装方式: 
        * 直接将target/release/es-plugins-demoPlugin-${version}.zip文件解压到${elasticsearch-home}/plugins/demoPlugin下面
       
* 测试：
    * 首先测试是否安装成功`bin/elasticsearch-plugin list`
    * 调用方式
curl -XGET localhost:9200/test-index/_search -d '{
  "query": {
    "function_score": {
      "query": {
        "match_all": {}
      },
      "script_score": {
        "script": {
          "inline":"demoPlugin",
          "lang": "native",
          "params": {
            "fisrt" : "test1" ,
            "send": "测试2" 
          }
        }
      }
    }
  },
  "sort":{
      "_score":{
        "order":"asc"
      }
    }
}'