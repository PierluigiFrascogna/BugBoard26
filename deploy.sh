#/bin/bash

set -euo pipefail

cd "$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"

git fetch --all --prune
git reset --hard origin/main

podman compose pull
podman compose up -d --remove-orphans

podman image prune -f >/dev/null 2>&1 || true