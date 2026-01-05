#!/bin/bash
set -euo pipefail

cd "$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"

PROJECT="bugboard26"
PROFILE="fullstack"

BASE_FILE="compose.base.yaml"
PROD_FILE="compose.prod.yaml"

WEB_CONTAINER="${PROJECT}_web_1"
USERS_CONTAINER="${PROJECT}_users_1"
BUGBOARD_CONTAINER="${PROJECT}_bugboard_1"

WEB_IMAGE="ghcr.io/pierluigifrascogna/bugboard26/web:latest"
USERS_IMAGE="ghcr.io/pierluigifrascogna/bugboard26/users:latest"
BUGBOARD_IMAGE="ghcr.io/pierluigifrascogna/bugboard26/bugboard:latest"

compose() {
  podman compose -f "$BASE_FILE" -f "$PROD_FILE" --profile "$PROFILE" "$@"
}

running_image_id() {
  # ID immagine usata dal container (sha256:...)
  local container="$1"
  podman inspect --format '{{.Image}}' "$container" 2>/dev/null || true
}

current_image_id() {
  # ID immagine locale per un image ref (dopo pull)
  local image="$1"
  podman image inspect "$image" --format '{{.Id}}' 2>/dev/null || true
}

has_changed() {
  # ritorna 0 (true) se l'immagine del container è diversa da quella attuale
  local container="$1"
  local image="$2"

  local running_id current_id
  running_id="$(running_image_id "$container")"
  current_id="$(current_image_id "$image")"

  [[ -z "$running_id" || -z "$current_id" || "$running_id" != "$current_id" ]]
}

remove_stack_containers() {
  podman rm -f \
    "$WEB_CONTAINER" \
    "$USERS_CONTAINER" \
    "$BUGBOARD_CONTAINER" \
    2>/dev/null || true
}

# ---- git update (compose ecc.) ----
git fetch --all --prune
git reset --hard origin/main

# ---- pull immagini ----
compose pull

# ---- check immagini ----
REDEPLOY=false
if has_changed "$WEB_CONTAINER" "$WEB_IMAGE"; then REDEPLOY=true; fi
if has_changed "$USERS_CONTAINER" "$USERS_IMAGE"; then REDEPLOY=true; fi
if has_changed "$BUGBOARD_CONTAINER" "$BUGBOARD_IMAGE"; then REDEPLOY=true; fi

# ---- redeploy se serve ----
if [[ "$REDEPLOY" == true ]]; then
  remove_stack_containers
fi

# ---- up ----
compose up -d --remove-orphans --no-build

podman image prune -f >/dev/null 2>&1 || true
