FROM texlive/texlive:latest

# AGGIUNTA: Aggiorna i pacchetti e installa Inkscape
RUN apt-get update && \
    apt-get install -y inkscape 

WORKDIR /BugBoard26/docs

COPY . .

CMD ["sleep", "infinity"]