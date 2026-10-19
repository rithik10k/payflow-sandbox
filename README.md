#PayFlow Sandbox

A production-grade open-source fintech system that demonstrates the patterns every payment engineer needs but no CS course teaches.

## What problems does this solve?

- **Duplicate payments** - Built-in idempotency prevents double charges even under network failures and retries

- **Silent webhook failures** - Chaos simulator stress-tests handlers with 150 dispute events in 10 seconds

- **Manual reconciliation** - Automated engine catches mismatches between your DB and bank statements with AI explanations

## Architecture

5 microservices | Apache Kafka | Spring Boot | React | Docker | AWS

## Quick start

git clone https://github.com/rithik10k/payflow-sandbox.git
cd payflow-sandbox
docker-compose up

## Tech stack

Java 21 | Spring Boot 3 | Apache Kafka | PostgreSQL | Redis | React 18 | Docker | AWS | Spring AI

## Author

github.com/rithik10k
