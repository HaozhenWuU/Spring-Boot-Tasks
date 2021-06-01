 # SQL vs MongoDB

 1 - Count of Records/Documents

```
 db.bios.count()

```

 2 - Find Bios with Birth Date before 1950

```
db.person.find(
	{birth: {$lt:ISODate("1950-01-01")} },
	{name : 1, birth : 1, _id : 0}

)

```

3 - Get a Unique Listing of all the Awards (in DB/Collection) granted

```
db.bios.distinct("awards.award")

```

4 - Get a Sorted Listing of all the First Names (ascending order)

```
db.bios.find(
	{},
	{"name.first":1,"_id":0}).sort({"name.first":1})

```

5 - Get a Sorted Listing of all the First Names (descending order)

```
db.bios.find(
	{},
	{"name.first":1,"_id":0}).sort({"name.first":-1})

```

6 - Count the number of BIOS that don't yet have an award

```
db.bios.find({awards:{$exists:false}}).count()

```

7 - Display the System ID (Primary Key) for the BIO in Query #6

```
db.bios.find({awards:{$exists:false}},{"_id":1})

```

8. Display names (first and last) along with awards and contributions from BIOS with 1 Contribution AND 2 Awards

```
db.bios.find({"contribs":{"$size": 1},"awards":{"$size": 2}},{"name":1, "contribs": 1,"awards.award":1})
```

9. Display names (first and last) along with awards and contributions from BIOS with 1 Contributions OR 2 Awards

```
db.bios.find({$or:[{"contribs": {"$size" : 1}},{"awards":{"$size": 2}}]},{"name":1,"contribs":1,"awards.award":1})

```

10 - List all the Awards for a BIO

```
db.bios.findOne({},{"name":1,"awards.award":1})

```
