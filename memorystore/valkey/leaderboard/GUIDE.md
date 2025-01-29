# Building a Leaderboard Service on Google Cloud using Valkey, Spring Boot, and PostgreSQL

Leaderboards are a useful way to display ranking data in applications. This guide explains how to create a scalable leaderboard system using Spring Boot, PostgreSQL, and Valkey (or Memorystore on GCP). By using a caching layer, developers can deliver real-time leaderboard rankings while reducing database load.

## Benefits of a Cached Leaderboard

- **Performance:** Leaderboards store ranking data in memory, enabling near-instantaneous retrieval of scores and rankings, reducing the time required to query and sort data from the database.
- **Reduced Latency:** Handles thousands or millions of updates and queries with minimal latency.
- **Database Efficiency** Caching frequently accessed leaderboard data minimizes the need for repetitive, and high volume database queries.

## What You’ll Build

You’ll set up a leaderboard service that:

1. **Stores leaderboard data in PostgreSQL** for persistence and historical analysis.
2. **Uses Valkey (Memorystore)** as an in-memory cache for updating scores and quick lookups.
3. **Spring Boot Applications** Exposes RESTful APIs for adding scores, retrieving rankings, and filtering leaderboards.
4. **Deploys on Google Cloud Platform (GCP)** using services like Cloud Run, Cloud SQL, and Memorystore.

By following this guide, you’ll implement a scalable leaderboard system with low latency and quick performance.

## Architecture Overview

- **Spring Boot Application:** Manages leaderboard logic and provides APIs for interaction.
- **Valkey (In-Memory Cache):** Stores active leaderboard data for quick lookups.
- **PostgreSQL Database:** Acts as the persistent storage for leaderboard data.
- **Google Cloud Platform Services:** Hosts the application and its dependencies.

## Leaderboard Workflow

1. **Score Submission:** A user submits a score, which is added to the cache and database.
2. **Rank Retrieval:** By default rankings are returned, displaying data ordered by the highest scores.
3. **Rank Retrieval (Filtered):** Rankings are displayed based on any applied filters.

## Step-by-Step Guide

To begin, we are will generate an API with the following routes:

_addScore_: Creates a new score.
_getScores_: Returns the leaderboard rankings.

### Creating a new application

The first step is to initialize a brand new Spring Boot application. The [official guide](https://spring.io/guides/gs/spring-boot) demonstrates how to generate a new project using [Spring Initializer](https://start.spring.io/).

1. Choose `Maven` as the project type for this demonstration..
2. Select Sprint Boot version 3.4.1
3. Complete the appropriate metadata.
4. Choose your preferred `Packing` for downloading.
5. Select `Java 17` for your Java version.
6. Finally, generate and extract the files.

### Installing additional dependencies

Next, ensure the following dependencies have been added to your POM.xml file.

#### Jedis

Add the folowing snippet toconnect directly to the Memorystore for Valkey instance.

```xml
<!-- Jedis: Redis Java Client -->
<dependency>
   <groupId>redis.clients</groupId>
   <artifactId>jedis</artifactId>
   <version>4.3.0</version> <!-- Use the latest version -->
</dependency>
```

#### Jakarta

To ensure that our api routes are correctly validated. Add the following dependency.
This dependency enables the use of annotations like `@NotNull` and `@Size` on classes to automatically enforce input constraints, reducing the need for manual validation logic.

```xml
<!-- Add Validation support-->
<dependency>
   <groupId>jakarta.validation</groupId>
   <artifactId>jakarta.validation-api</artifactId>
   <version>3.0.2</version>
</dependency>
```

### Connecting the Service layer

Next, add the following to route logic to the API.

#### Adding a new Score

```java
// Code snippet

```

#### Retrieving Rankings

When reading entries from a Leaderboard, there are a number of ways to filter the resulting dataset:

##### Default search

```java
// code snippet

```

##### Using ZRANK to filter based on a username

```java
// code snippet

```

##### Using ZREVRANGE to reveerse the order based on scores

```java
// code snippet

```

## Scaling and Optimization

As traffic increases, the architecture can scale horizontally:

- **Cloud Run** can automatically scale instances based on load.
- **Memorystore (Valkey)** can be sized or upgraded to handle more cached data or higher throughput.
- **Cloud SQL** can scale vertically or horizontally (with read replicas) as needed.

You can fine-tune cache expiration strategies (TTL values) and eviction policies, depending on your data access patterns.

## Conclusion

By implementing this leaderboard system, you can easily display large amounts of sorted datasets, while also having the support to effectivly filter the data. Leveraging caching with Valkey (Memorystore) significantly reduces database load while maintaining fast and reliable user experiences. Running it in Google Cloud extends these benefits further, providing managed services and easy scaling.

For more information check out the [repository](https://github.com/GoogleCloudPlatform/java-docs-samples/tree/main/memorystore/valkey/leaderboard) for the full project details and follow the instructions to get started.
