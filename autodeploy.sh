#/bin/bash

set -euo pipefail

BRANCH="${1:-main}"
INTERVAL="${2:-30}"
BASE_FILE="${3:-compose.base.yaml}"
PROD_FILE="${4:-compose.prod.yaml}"
PROFILE="${5:-fullstack}"

cd "$(dirname "$0")"

if ! podman compose version >/dev/null 2>&1; then
  echo "Errore: 'podman compose' non disponibile sul server."
  exit 1
fi

LAST=""

while true; do
  git fetch origin "$BRANCH" >/dev/null 2>&1 || true
  REMOTE="$(git rev-parse "origin/$BRANCH" 2>/dev/null || echo '')"

  if [ -n "$REMOTE" ] && [ "$REMOTE" != "$LAST" ]; then
    echo "[autodeploy] new commit $REMOTE"

    git checkout "$BRANCH" >/dev/null 2>&1 || true
    git reset --hard "origin/$BRANCH"
    git clean -fd

    # rebuild + redeploy (base + prod) + profilo
    podman compose \
      -f "$BASE_FILE" \
      -f "$PROD_FILE" \
      --profile "$PROFILE" \
      up -d --build

    # pulizia immagini inutilizzate (opzionale)
    podman image prune -f || true

    LAST="$REMOTE"
  fi

  sleep "$INTERVAL"
done