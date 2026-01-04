#/bin/bash

set -euo pipefail

BRANCH="${1:-main}"
INTERVAL="${2:-30}"
COMPOSE_FILE="${3:-compose.prod.yaml}"

cd "$(dirname "$0")"

# scegli il comando compose disponibile
if command -v podman-compose >/dev/null 2>&1; then
  COMPOSE=(podman-compose -f "$COMPOSE_FILE")
elif podman compose version >/dev/null 2>&1; then
  COMPOSE=(podman compose -f "$COMPOSE_FILE")
else
  echo "Errore: non trovo né podman-compose né 'podman compose'. Installa podman-compose."
  exit 1
fi

git fetch origin "$BRANCH" >/dev/null 2>&1 || true
git checkout "$BRANCH" >/dev/null 2>&1 || true

LAST=""

while true; do
  git fetch origin "$BRANCH" >/dev/null 2>&1 || true
  REMOTE="$(git rev-parse "origin/$BRANCH" 2>/dev/null || echo '')"

  if [ -n "$REMOTE" ] && [ "$REMOTE" != "$LAST" ]; then
    echo "[autodeploy] new commit $REMOTE"
    git reset --hard "origin/$BRANCH"
    git clean -fd

    # rebuild + redeploy
    "${COMPOSE[@]}" up -d --build

    # pulizia immagini inutilizzate (opzionale)
    podman image prune -f || true

    LAST="$REMOTE"
  fi

  sleep "$INTERVAL"
done
