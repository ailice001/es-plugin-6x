* 环境依赖：
    * elasticsearch >= 5.x
    * pom中对不同elasticsearch版本的依赖，最终插件只能应用在对应版本的elasticsearch上

* 编译安装：
    * 编译：mvn clean package -DskipTests
    * 安装方式: 
        * 直接将target/release/es-plugins-demo-${version}.zip文件解压到${elasticsearch-home}/plugins/下面
       
* 测试：
    * 首先测试是否安装成功`bin/elasticsearch-plugin list`

----测试数据    这里使用kibana的DevTools
PUT test-index/default/1
{
  "test":"one",
  "other":"1"
}

PUT test-index/default/2
{
  "test":"two",
  "other":"1"
}

PUT test-index/default/3
{
  "test":"test1",
  "other":"1"
}

PUT test/default/4
{
  "test":"three",
  "other":"1"
}

PUT test-index/default/5
{
  "test":"test2",
  "other":"1"
}

--  调用方式
GET test/_search 
{
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
              "fisrt" : "test" ,
              "send": "test" 
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
  }
  
--结果   包裹test的返回1 否则返回最大的Double值
  {
    "took": 11,
    "timed_out": false,
    "_shards": {
      "total": 5,
      "successful": 5,
      "failed": 0
    },
    "hits": {
      "total": 5,
      "max_score": null,
      "hits": [
        {
          "_index": "test",
          "_type": "default",
          "_id": "5",
          "_score": 1,
          "_source": {
            "test": "test2",
            "other": "1"
          },
          "sort": [
            1
          ]
        },
        {
          "_index": "test",
          "_type": "default",
          "_id": "3",
          "_score": 1,
          "_source": {
            "test": "test1",
            "other": "1"
          },
          "sort": [
            1
          ]
        },
        {
          "_index": "test",
          "_type": "default",
          "_id": "2",
          "_score": 3.4028235e+38,
          "_source": {
            "test": "two",
            "other": "1"
          },
          "sort": [
            3.4028235e+38
          ]
        },
        {
          "_index": "test",
          "_type": "default",
          "_id": "4",
          "_score": 3.4028235e+38,
          "_source": {
            "test": "three",
            "other": "1"
          },
          "sort": [
            3.4028235e+38
          ]
        },
        {
          "_index": "test",
          "_type": "default",
          "_id": "1",
          "_score": 3.4028235e+38,
          "_source": {
            "test": "one",
            "other": "1"
          },
          "sort": [
            3.4028235e+38
          ]
        }
      ]
    }
  }
  
----- 使用curl执行
  curl -XGET localhost:9200/test/_search -d '{
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
              "fisrt" : "test" ,
              "second": "test" 
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
