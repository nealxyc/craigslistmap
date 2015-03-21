# API v0.1 #

  * [Search](API#Search.md)
  * [Address](API#Address.md)

---

## Search ##
```
http://craigslistmap.nealxyc.com/api/search
```

**Parameters**
> Required
    * url: the URL of search result from craigslist
> Optional
    * count: The maximum number of results that will be returned. Default value is 25.
**Return**
> An array of items. Item is in the following format.
```
{
"title":"String",//The title of the post
"address":"String",
"description":"String",
"date":Integer,//The number of milliseconds since January 1, 1970, 00:00:00 GMT 
 "url":"String"
}
```
**Example**

```
 http://craigslistmap.nealxyc.com/api/search?url=http%3A%2F%2Fsfbay.craigslist.org%2Fsfc%2Fapa%2F&count=25
```

---



## Address ##
```
http://craigslistmap.nealxyc.com/api/address
```

**Parameters**
> Required
    * url: the URL of post from craigslist
**Return**
> Item is in the following format.
```
{
"address":"String",
 "url":"String"
}
```
**Example**

```
 http://craigslistmap.nealxyc.com/api/address?url=http%3A%2F%2Fsfbay.craigslist.org%2Fsfc%2Fapa%2F2418119418.html
```