# crawler
启动是从Test.main启动的  
爬下来的数据在data/result.json中  
开发的时候用了mock数据  
实际运行时在Header里加了Cookie，每爬一页停一秒，所以没有被限制 
提供了一个AmazonProductToAdPoConverter，model转换，keyword生成等都在Converter里  
时间太赶，本来想用Spring Boot Application加一点前端UI，没做完，现提供了demo版，从Test.main启动运行 
