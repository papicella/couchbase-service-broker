<H1>Couchbase 4.6.x Service Broker for Pivotal Cloud Foundry </h1>

The following example is a PCF 2.0 Service Broker written as a Spring Boot application. This is just an example and should
be evolved to match a production type setup in terms of bucket creation and access for created service instances.

<h2> Install </h2>

- Clone as follows

```
git clone https://github.com/papicella/couchbase-service-broker.git
```

- Edit src/main/resources/application.yml to provide bucket details and a main Administrator username/password. The "security." 
properties use Spring Security BASIC HTTP Authentication to protect the service broker application endpoints 

```
couchbase:
  host: pas-macbook.local
  bucketName: default
  bucketPassword:
  clusterAdminUser: Administrator
  clusterAdminPassword: welcome1
security:
  user:
    name: user
    password: password
```
 
- Package as shown below

```
$ mvn -DskipTests=true package

...

[INFO] --- maven-jar-plugin:2.6:jar (default-jar) @ couchdb-service-broker ---
[INFO] Building jar: /Users/pasapicella/pivotal/DemoProjects/spring-starter/pivotal/couchdb-service-broker/target/couchdb-service-broker-0.0.1-SNAPSHOT.jar
[INFO]


```

- Push to CF as follows
 
```
$ cf push couchbase-service-broker-pas -p ./target/couchdb-service-broker-0.0.1-SNAPSHOT.jar -m 1g

...

Successfully destroyed container

0 of 1 instances running, 1 starting
1 of 1 instances running

App started


OK

...

     state     since                    cpu    memory         disk             details
#0   running   2017-08-29 11:21:07 PM   7.8%   545.5M of 1G   146.5M of 512M


```

- View pushed application 

```
pasapicella@pas-macbook:~$ cf apps
Getting apps in org pcfdev-org / space pcfdev-space as admin...
OK

name                           requested state   instances   memory   disk   urls
pas-springboot-pcf             started           1/1         756M     512M   pas-springboot-pcf-enharmonic-iceman.local.pcfdev.io
couchbase-service-broker-pas   started           1/1         1G       512M   couchbase-service-broker-pas.local.pcfdev.io
```

- Create a service broker 

```
pasapicella@pas-macbook:~$ cf create-service-broker couchbase user password http://couchbase-service-broker-pas.local.pcfdev.io
Creating service broker couchbase as admin...
OK
```

- View service brokers to ensure it now exists

```
pasapicella@pas-macbook:~$ cf service-brokers
Getting service brokers as admin...

name           url
couchbase      http://couchbase-service-broker-pas.local.pcfdev.io
local-volume   http://localbroker.local.pcfdev.io
mongodb        http://mongodb-service-broker.local.pcfdev.io
p-mysql        http://mysql-broker.local.pcfdev.io
p-rabbitmq     http://rabbitmq-broker.local.pcfdev.io
p-redis        http://redis-broker.local.pcfdev.io
```

- Enable the service broker

```
pasapicella@pas-macbook:~$ cf enable-service-access couchbase
Enabling access to all plans of service couchbase for all orgs as admin...
OK
```

- Log into Application Manager UI and verify service appears in Market Place

https://image.ibb.co/n92FRv/couchbase_sb_1.png

![alt tag](https://image.ibb.co/n92FRv/couchbase_sb_1.png)
![alt tag](https://image.ibb.co/fFX4KF/couchbase_sb_2.png)
![alt tag](https://image.ibb.co/m7ctDa/couchbase_sb_3.png)


- 

<hr />
Pas Apicella [papicella at pivotal.io] is a Senior Platform Architect at Pivotal Australia 
