# Building a Caching Service on Google Cloud using Valkey, Spring Boot, and PostgreSQL

Modern applications need to deliver fast, responsive user experiences at scale.

In this tutorial, we’ll walk through the architectural concepts and deployment steps for creating a high-performance caching service on Google Cloud. Using a combination of Java, Spring Boot, PostgreSQL, and [Valkey](https://github.com/invertase/valkey-demos) you can significantly reduce latency, while also recuding the load on your database.

## Why Caching Matters

- **Speed & Latency:** Storing frequently requested data in memory avoids repeated round-trip queries to databases, whilst reducing response times.
- **Scalability:** By reducing the workload on your database, applications can serve data directly from memory, increasing the capacity for requests.

## What You’ll Build

You’ll set up a caching service that:

1. **Works with a PostgreSQL database** to store long-lived, persistent records.
2. **Incorporates Valkey** as a high-speed, in-memory cache, fronting the PostgreSQL database.
3. **Uses Spring Boot** to expose REST endpoints, providing a simple interface for reading, writing, and invalidating cached data.
4. **A solution that can be Dockerized and deployed to Google Cloud Platform (GCP)** for production, leveraging services like Cloud Run, Cloud SQL, and Memorystore.

By following this guide, you’ll have a reference architecture ready to adapt, test, and deploy to meet the performance needs of your application.

## Architecture Overview

- **Spring Boot Application:** Serves as the middle tier for responding to API calls. When a request is received, the API checks Valkey for cached results; if no entries are found, then the API will retrieve data from the PostgreSQL database and update the cache.
- **Valkey (In-Memory Cache):** A Redis-like memory store that keeps hot data ready to be served instantly.
- **PostgreSQL Database:** Your source of truth for all data. The cache reduces how often the app queries this database.
- **Google Cloud Infrastructure:** Deployed using Terraform, you can host the application on Cloud Run, store data in Cloud SQL for PostgreSQL, and leverage Memorystore for Valkey.

## Steps to Build

1. **Set Up Your Environment:**
   To get started, we will need to install Docker and ensure that we have a project setup in GCP with the required APIs enabled.

   ## Install Docker on your local machine

   - [Docker](https://www.docker.com)
   - [Docker Compose](https://docs.docker.com/compose/)

   ### Setting up your GCP Project

   - **GCP Project**: If a project does already exist, you can set up a new project through the [GCP console](https://console.cloud.google.com/welcome).
   - **Enable Cloud Run API**: Enable the [Cloud Run API](https://console.cloud.google.com/apis/api/run.googleapis.com/metrics?inv=1&invt=AbnBKQ).
   - **Enable Memorystore API**: Enable the [Memorystore API](https://console.cloud.google.com/marketplace/product/google/memorystore.googleapis.com).
   - **Cloud SQL API**: Enable the [Cloud SQL API](https://console.cloud.google.com/marketplace/product/google/sqladmin.googleapis.com).

2. **Download the Example Code:**
   Instead of writing all the code from scratch, we’ve prepared a working demo repository that you can clone and explore. This contains everything you need including Spring Boot configuration, caching logic, Dockerfiles, and Terraform scripts for deployment.

   **Get the code here:**  
   [https://github.com/GoogleCloudPlatform/java-docs-samples/tree/main/memorystore/valkey/cache](https://github.com/GoogleCloudPlatform/java-docs-samples/tree/main/memorystore/valkey/cache)

3. **Run Locally with Docker Compose:**
   Use Docker Compose to start PostgreSQL, Valkey, and your Spring Boot app together. You’ll have a fully functional local environment that demonstrates how caching accelerates data retrieval.

   Simply run:

   ```bash
   docker compose up --build
   ```

   Once started, you can make `GET`, `POST`, and `DELETE` requests to the REST endpoints to store, retrieve, and invalidate cached data. Check the repository’s README for example commands and endpoints.

4. **Deploying to GCP (Optional):**

### Terraform

Terraform is an Infrastructure as Code (IaC) tool that allows you to define and provision cloud resources in a repeatable, automated way.

If not already download, you can down from this [source](https://developer.hashicorp.com/terraform/tutorials/gcp-get-started).

With Terraform, you can provision:

- **Cloud Run:** Runs your containerized Spring Boot application in a fully managed, serverless environment.
- **Cloud SQL for PostgreSQL:** Provides a fully managed PostgreSQL instance.
- **Memorystore (Valkey):** A fully managed Redis-compatible cache.

  Adjust the Terraform variables for your project, initialize Terraform, and apply the configuration. Once deployed, set up environment variables in Cloud Run to point to the appropriate instances.

  Visit the repository’s Terraform directory for detailed instructions and run:

  ```bash
  terraform init
  terraform apply
  ```

  After Terraform has deployed your infrastructure, you’ll have a production-ready caching architecture running in the cloud.

## Scaling and Optimization

As traffic increases, the architecture can scale horizontally:

- **Cloud Run** can automatically scale instances based on load.
- **Memorystore (Valkey)** can be sized or upgraded to handle more cached data or higher throughput.
- **Cloud SQL** can scale vertically or horizontally (with read replicas) as needed.

You can fine-tune cache expiration strategies (TTL values) and eviction policies, depending on your data access patterns.

## Conclusion

By combining an in-memory store (Valkey) with a reliable database (PostgreSQL), all orchestrated by a Spring Boot application, you’ve built a caching solution that delivers high performance, reduces database load, and ensures an excellent user experience. Running it in Google Cloud extends these benefits further, providing managed services and easy scaling.

For more information check out the [repository](https://github.com/GoogleCloudPlatform/java-docs-samples/tree/main/memorystore/valkey/caching)) for the full project details and follow the instructions to get started:
