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
收费模式以及默认方式
什么时候开始限流
是不是默认autoscaling是不是默认的
默认的流量是多少
加密
数据类型： S-String，B-Binary, N-Number
如果有一个Global Index,是不是代表全局的memberkey只能有一个？put的时候是否会失败
Index的attr，是不是代表attr都是必须的
