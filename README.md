
# Provider service

Part of a multi module project. Handles accounts and their respective roles.

## Features

- Store provider data and their catalog
- CRUD operations for providers and items
- Account role verification from [Account-service](https://github.com/NikoIskra/account-service) for non GET methods
- Unit and integration tests for positive and negative scenarios
- Code generation using openAPI
- Get methods featuring Spring pageination
## Tech Stack

- Java Spring Boot
- PostgreSQL
- Dockerfile and docker-compose file
- Custom network inside docker-compose file, same with [Account-service](https://github.com/NikoIskra/provider-service)
- Spotless Gradle
- GitHub actions which ensure all tests pass and Spotless has been applied
- Flyway migration scripts
- API specification using OpenAPI specification

## API Reference

#### Healtcheck

```http
  GET /api/v1/healthcheck
```

#### Create Provider [.http file](https://github.com/NikoIskra/provider-service/blob/main/requests/create_provider.http)

```http
  POST /api/v1/provider
```

Header:
| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `X-ACCOUNT-ID`      | `string(uuid)` | **Required**. id of account that has provider role|

Request body:

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `name`      | `string` | **Required**. provider name, 5 > length < 64 |
| `title`      | `string` | **Required**. provider title, 5 > length < 128 |
| `description`      | `string` | **Optional**. provider description |
| `phoneNumber`      | `string` | **Required**. phone number regex |

### GET all Providers

[.http file](https://github.com/NikoIskra/provider-service/blob/main/requests/retrieve_all_providers.http)

```http
  GET /api/v1/provider
```

Header:
| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `X-ACCOUNT-ID`      | `string(uuid)` | **Required**. id of account that has provider role|
| `page`      | `integer` | **Required**. page number to retrieve|
| `page-size`      | `integer` | **Optional**. ammount of records to display on 1 page (default page size adjustable in application.properties)|

#### Patch Provider [.http file](https://github.com/NikoIskra/provider-service/blob/main/requests/update_provider.http)

```http
  PATCH /api/v1/provider/{provider-id}
```

Header:
| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `X-ACCOUNT-ID`      | `string(uuid)` | **Required**. id of account that has provider role|
| `provider-id`      | `integer` | **Required**. id of provider|

Request body:

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `title`      | `string` | **Optional**. provider title, 5 > length < 128 |
| `description`      | `string` | **Optional**. provider description |
| `status`      | `string` | **Optional**. provider status. Possible values: ["active", "suspended", "view-only", "cancelled"]  |
| `phoneNumber`      | `string` | **Optional**. phone number regex |

#### Create Item [.http file](https://github.com/NikoIskra/provider-service/blob/main/requests/create_item.http)

```http
  POST /api/v1/provider/{provider-id}/item
```

Header: 

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `account-id`      | `uuid` | **Required**. id of account to set role |
| `provider-id`      | `integer` | **Required**. id of provider|

Request body:
| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `title`      | `string` | **Required**. title of item|
| `description`      | `string` | **Optional**. item description|
| `priceCents`      | `integer` | **Required**. price of item|
| `subItems`      | `array` | **Optional**. array of SubItem models, each with their own: title, description, priceCents|


#### Update item [.http file](https://github.com/NikoIskra/provider-service/blob/main/requests/update_item.http)

```http
  PUT /api/v1/provider/{provider-id}/item/{item-id}
```

Header:
| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `X-ACCOUNT-ID`      | `string(uuid)` | **Required**. id of account that has provider role|
| `provider-id`      | `integer` | **Required**. id of provider|
| `item-id`      | `integer` | **Required**. id of item|

Request body:

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `title`      | `string` | **Optional**. provider title, 5 > length < 128 |
| `description`      | `string` | **Optional**. item description |
| `priceCents`      | `integer` | **Optional**. item price |
| `status`      | `string` | **Optional**. item status. Possible values: ["active", "suspended", "view-only", "cancelled"]  |

#### Get item [.http file](https://github.com/NikoIskra/provider-service/blob/main/requests/retrieve_item.http)

```http
  GET /api/v1/provider/{provider-id}/item/{item-id}
```

Header: 

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `X-ACCOUNT-ID`      | `string(uuid)` | **Required**. id of account that has provider role|
| `provider-id`      | `integer` | **Required**. id of provider|
| `item-id`      | `integer` | **Required**. id of item|

#### Get by title [.http file](https://github.com/NikoIskra/provider-service/blob/main/requests/search_by_title.http)

```http
  GET /api/v1/title/search
```

Header:
| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `X-ACCOUNT-ID`      | `string(uuid)` | **Required**. id of account that has provider role|
| `query`      | `string` | **Required**. string to look for in title|
| `order-by`      | `string` | **Optional**. what variable to order-by, "created-at" only allowed|
| `order`      | `string` | **Optional**. order ASC or DESC|
| `page`      | `integer` | **Required**. page number to retrieve|
| `page-size`      | `integer` | **Optional**. ammount of records to display on 1 page (default page size adjustable in application.properties)|

## Database model
provider:

- `ID : long`
- `OwnerId : uuid`
- `Name : string`
- `Title : string`
- `Description : string`
- `statusEnum : string`
- `CreatedAt : timestamp`
- `UpdatedAt : timestamp`

Item:

- `ID : long`
- `Title : string`
- `Description : string`
- `PriceCents : integer`
- `statusEnum : string`
- `CreatedAt : timestamp`
- `UpdatedAt : timestamp`


Sub Item:

- `ID : long`
- `Title : string`
- `Description : string`
- `PriceCents : integer`
- `statusEnum : string`
- `CreatedAt : timestamp`
- `UpdatedAt : timestamp`

## Environment Variables

To run this project, you will need to add the following environment variables to your .env file

`DB_HOST`

`POSTGRES_DB`

`POSTGRES_USER`

`POSTGRES_PASSWORD`


## Run Locally

Clone the project

```
  git clone https://github.com/NikoIskra/provider-service
```

Go to the project directory

```
  cd provider-service
```

Build .jar file

```
  ./gradlew build
```

Start WSL and locate service directory

```
  cd /mnt/c/provider-service
```

Run docker compose

```
docker compose up
```

