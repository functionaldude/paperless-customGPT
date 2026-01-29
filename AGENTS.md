## What this repo is

This app exposes some basic REST endpoints, so a custom ChatGPT agent can interact with it.
At the same time it generates and stores documents in a RAG (postgres + pgvector) setup, so that the agent can use them
as context for its answers.
The documents are coming from an existing paperless instance, it is not part of this project. This project is only a
complimentary service, but it is attached to the same DB as paperless.

## Setup

- Basic kotlin+gradle project
- Uses spring for REST endpoints
- Uses jOOQ for DB access
- Uses langchain4j for RAG functionality