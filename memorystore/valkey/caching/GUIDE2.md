# Building a Caching Service on Google Cloud using Valkey, Spring Boot, and PostgreSQL

ValKey is a globally distributed, in-memory key-value store offered as part of Google Cloud Platform (GCP). It's ideal for use cases requiring extremely low latency and high throughput, such as caching. This guide explores how to use ValKey for caching on GCP, covering benefits, basic operations, and key concepts like Time-to-Live (TTL).

## Why Cache with ValKey?

Caching frequently accessed data can significantly improve application performance and reduce backend load. Here's why ValKey is a strong choice for caching on GCP:

- **Low Latency**: ValKey's in-memory storage provides extremely fast read and write speeds, minimizing latency for cached data retrieval.
- **High Throughput**: ValKey is designed to handle a massive number of requests per second, making it suitable for high-traffic applications.
- **Global Distribution**: With its global presence, ValKey can serve cached data from locations close to your users, further reducing latency.
- **Scalability**: ValKey automatically scales to accommodate your needs, ensuring consistent performance even under heavy load.
- **Integration with GCP**: ValKey seamlessly integrates with other GCP services, simplifying deployment and management.

## Common Caching Use Cases

Caching has many advantages including but not limited to:

- **HTTP Request Caching**: Store responses from frequently accessed APIs or web pages in ValKey to accelerate page load times and reduce server load.
- **Session Management**: Cache user session data in ValKey for fast access and improved user experience.
- **Database Query Caching**: Store results of frequently executed database queries in ValKey to offload your database and improve response times.

## ValKey Operations for Caching

In this use-case we are goign to demonstrate how we can apply a Caching solution using Valkey for MemoryStore in `Java` using the Jedis client.

## Application Design

In this application, we are going to create a Basic CRUD API that will enable users to retrieve, create and delete items in a database.

The structure comprises of 3 distinct layers:

**_API Layer_**: This is our Java based API which will define routes for each action.
**Caching Layer**: Using Valkey for MemoryStore, this will a low latency approach for retrieving data that we have stored once it has retrieved from the database.
**Database Layer**: Our main datasource and source of truth. This Google Coud SQL database will store all of the items that have been creared.

//Add Diagram of archesecture here

### Writing to the cache

```java
//code sample
```

### Reading from the Cache (Retrieving Values)

```java
//code sample
```

### Deleting from the Cache (Invalidating Entries)

```java
//code sample
```

#### Time-to-Live (TTL)

ValKey allows you to set a TTL for cached entries, ensuring automatic expiration and preventing stale data. You can specify the TTL during entry creation:

```java
//code sample

```

## Conclusion

By combining an in-memory store (Valkey) with a reliable database (PostgreSQL), all orchestrated by a Spring Boot application, you can build a caching solution that delivers high performance, reduces database load, and ensures an excellent user experience. Running it in Google Cloud extends these benefits further, providing managed services and easy scaling.

For more information check out the [repository](https://github.com/GoogleCloudPlatform/java-docs-samples/tree/main/memorystore/valkey/caching)) for the full project details and follow the instructions to get started:
