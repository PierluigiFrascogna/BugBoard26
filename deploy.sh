#!/bin/bash

date +"[%Y-%m-%d %H:%M:%S] Starting deployment..."

set -euo pipefail

cd "$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"

PROJECT="bugboard26"
PROFILE="fullstack"
BASE_FILE="compose.base.yaml"
PROD_FILE="compose.prod.yaml"

compose() {
  podman compose \
    -p "$PROJECT" \
    -f "$BASE_FILE" \
    -f "$PROD_FILE" \
    --profile "$PROFILE" \
    "$@"
}

compose down --remove-orphans -v || true

git fetch --all --prune
git reset --hard origin/main

compose pull
compose up -d --remove-orphans

podman image prune -f >/dev/null 2>&1 || true

date +"[%Y-%m-%d %H:%M:%S] Deployment finished."