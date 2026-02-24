# Mikroservisi i Kubernetes — Treća laboratorijska vježba

> **Predmet:** Raspodijeljeni sustavi — FER, Diplomski studij, ak. god. 2025./2026.

## Pregled projekta

Raspodijeljeni sustav za praćenje očitanja temperature i vlage, implementiran kao mikroservisna arhitektura orkestrirana putem Kubernetesa.

Sustav se sastoji od tri mikroservisa:
- **Temperature Microservice** – izlaže HTTP sučelje koje vraća trenutnu temperaturu u Celzijusima ili Kelvinima
- **Humidity Microservice** – izlaže HTTP sučelje koje vraća trenutnu vlagu u postocima
- **Aggregator Microservice** – poziva oba mikroservisa, agregira odgovore i vraća ih korisniku

---

## Arhitektura

```
Korisnik
   │
   ▼
[Aggregator Microservice]
   ├──► [Temperature Microservice]
   └──► [Humidity Microservice]
```

Svi mikroservisi pokrenuti su unutar Kubernetesovog grozda upravljanog putem **Rancher Desktop**.

---

## Tehnologije

| Tehnologija | Verzija |
|---|---|
| Java | 21 |
| Spring Boot | 3.4.0 |
| Spring Dependency Management | 1.1.6 |
| Gradle | 9.1 |
| Kubernetes (Rancher Desktop) | — |

---

## Struktura projekta

```
.
├── temperature-microservice/
│   ├── build.gradle
│   ├── gradle/
│   ├── gradlew
│   ├── gradlew.bat
│   ├── settings.gradle
│   └── src/
├── humidity-microservice/
│   ├── build.gradle
│   ├── gradle/
│   ├── gradlew
│   ├── gradlew.bat
│   ├── settings.gradle
│   └── src/
├── aggregator-microservice/
│   ├── build.gradle
│   ├── gradle/
│   ├── gradlew
│   ├── gradlew.bat
│   ├── settings.gradle
│   └── src/
└── k8s/
    ├── deployment-instructions.txt
    └── config/
        └── *.yaml
```

---

## Mikroservisi

### Temperature Microservice

Vraća trenutnu temperaturu. Podržava Celzijuse i Kelvine (konverzija se vrši u Aggregator mikroservisu).

**Primjer odgovora (Celzijusi):**
```json
{
  "name": "Temperature",
  "unit": "C",
  "value": 27
}
```

**Primjer odgovora (Kelvini):**
```json
{
  "name": "Temperature",
  "unit": "K",
  "value": 300.15
}
```

---

### Humidity Microservice

Vraća trenutnu relativnu vlagu u postocima.

**Primjer odgovora:**
```json
{
  "name": "Humidity",
  "unit": "%",
  "value": 48
}
```

---

### Aggregator Microservice

Poziva mikroservise za temperaturu i vlagu, agregira odgovore i vraća ih korisniku. Ne pohranjuje agregirane podatke u bazu.

Mjerna jedinica temperature konfigurira se u `application.properties` / `application.yml`. URL-ovi mikroservisa za temperaturu i vlagu dohvaćaju se iz konfiguracijske datoteke i koristiti DNS ime Kubernetesovog `Service` resursa.

**Primjer odgovora:**
```json
[
  {
    "name": "Humidity",
    "unit": "%",
    "value": 48
  },
  {
    "name": "Temperature",
    "value": 27,
    "unit": "C"
  }
]
```

---

## Emulacija senzora

Budući da pravi senzori nisu spojeni, očitanja se emuliraju iz datoteke `readings.csv`. Broj retka određuje se formulom:

```
red = (brojAktivnihSekundi % 100) + 1
```

gdje je `brojAktivnihSekundi` razlika između trenutnog vremena i ponoći 1. siječnja 1970. UTC, mjerena u **minutama**.

Prilikom pokretanja, mikroservis čita datoteku i upisuje sva očitanja u in-memory bazu podataka (npr. H2).

---

## Kubernetes konfiguracija

Za svaki mikroservis potrebno je definirati sljedeće Kubernetes resurse u YAML datotekama:

- `Deployment` — definira pokretanje kontejnera
- `Service` — izlaže mikroservis unutar grozda putem DNS imena
- `ConfigMap` — ucitava `readings.csv` izvana u kontejner (za mikroservise temperature i vlage) te konfiguracijsku datoteku izvana (za aggregator mikroservis)

Aggregator mikroservis dodatno zahtijeva `Gateway` resurs za komunikaciju izvan grozda.

### Docker image

Docker slike grade se Gradle zadatkom:

```bash
./gradlew bootBuildImage
```

### Postavljanje na Kubernetes

Detaljne upute s točnim naredbama i primjerom GET zahtjeva nalaze se u:

```
k8s/deployment-instructions.txt
```

Primjer naredbe:

```bash
kubectl apply -f k8s/config/humidity-deployment.yaml
kubectl apply -f k8s/config/temperature-deployment.yaml
kubectl apply -f k8s/config/aggregator-deployment.yaml
