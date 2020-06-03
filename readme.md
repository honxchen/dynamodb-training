## setup
```
docker run -p 8000:8000 amazon/dynamodb-local

aws dynamodb create-table --endpoint-url http://localhost:8000 \
    --table-name Project_hongxing \
    --attribute-definitions AttributeName=projectName,AttributeType=S AttributeName=projectType,AttributeType=S \
    --key-schema AttributeName=projectName,KeyType=HASH AttributeName=projectType,KeyType=RANGE \
    --provisioned-throughput ReadCapacityUnits=5,WriteCapacityUnits=5

 ./gradlew bootRun
```




1. describe
```
aws dynamodb   describe-table --table-name Project_hongxing
```
2. put item
```
aws dynamodb put-item --table-name Project_hongxing --item file://member.json
aws dynamodb batch-write-item --request-items file://members.json
```

3. get
```
aws dynamodb get-item --table-name Project_hongxing --key  file://key.json
```
4. query by key
```
aws dynamodb query  --table-name Project_hongxing \
 --key-condition-expression "projectName=:projectName and projectType=:projectType" \
 --expression-attribute-values  file://query-key-expression-attributes.json
```
query by member 查询的时候必须要的有primaryKey, scan的时候可以直接用memeberName进行过滤

5. scan
```
aws dynamodb scan --table-name Project_hongxing  \
--filter-expression  "memberName=:mn" \
--expression-attribute-values '{":mn":{"S": "hongxing"}}'
```

6. create index with memberName
```
aws dynamodb update-table --table-name Project_hongxing \
--attribute-definitions='[{"AttributeName": "memberName", "AttributeType": "S"}]' \
--global-secondary-index-updates=file://member-global-index.json
```

7. query by index
```
aws dynamodb query  --table-name Project_hongxing --index-name=memberNameIndex \
--key-condition-expression "memberName=:memberName" \
--expression-attribute-values  file://query-memberName-expression-attributes.json
```
如果index创建之后Detecting and Correcting Index Key Violations


Q：
* SQL 与 NOSQL的区别
  SQL架构中，数据scheme定义清晰，而NoSql是一个键值对存储数据，数据结构定义较为灵活。
  * SQL
    * 适合存储结构化数据
    * 支持表之间的关联，更好的支持数据中心化，避免冗余
    * 支持SQL语句，语法强大，对于支持基本以及复杂语法，例如join等
  * NoSQL
    * 适合存储半结构化数据
    * 数据结构灵活，支持项目不断演进，方便快速开始
    * 大数据场景下更好进行扩容

* PrimaryKey: 唯一标识Item的key,可以包含一个属性(Partition key )或者两个属性(Partition key and sort key )，在create table的时候必须进行定义
* Index: 在表中叫做Secondary Indexes, 可以是Global或者local, 增加表在查询时候的灵活度
* 收费模式以及默认方式： 默认方式Provisioned Mode，也就是提前为程序设置了read/write每秒最大次数
* 什么时候开始限流：
    * Provisioned Mode模式下，当超过设置的次数，就开始限流，如果是autoscaling开启的话，不会进行限流
    * On-Demand Mode模式下，一般会预留上一次峰值的两倍unit,如果超过了，则会开始限流
* 是不是默认autoscaling是不是默认的: 默认开启
* 加密
* 数据类型： S-String，B-Binary, N-Number
* 如果有一个Global Index,是不是代表全局的memberkey只能有一个？put的时候是否会失败： 是的
* Index的attr，是不是代表attr都是必须的
* ProjectionExpression: return only some of the attributes.
* KeyConditionExpression: parameter specifies the key values that you want to query
* ExpressionAttributeValues: This is analogous to the use of bind variables in relational databases, where you substitute the actual values into the SELECT statement at runtime.
* FilterExpression: remove certain items from the results before they are returned to you.
* https://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/credentials.html
