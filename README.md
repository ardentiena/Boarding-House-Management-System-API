# Boarding House Management System API

### 🇮🇩 Bahasa Indonesia

> **⚠️ STATUS: PENELITIAN / EKSPERIMEN**
>
> Repository ini berisi source code dari penelitian tugas akhir (skripsi) dengan judul:
> *"Penerapan Containerization untuk Portabilitas Deployment: Studi Kasus REST API Sistem Manajemen Kos"*
>
> **Repository ini untuk pengembangan dan build image.**
> Jika Anda hanya ingin **mendeploy aplikasi** (tanpa build source code), gunakan repository deployment:
> 👉 [boardinghouse-api](https://github.com/ardentiena/Boarding-House-API.git)

---

### 🇬🇧 English

> **⚠️ STATUS: RESEARCH / EXPERIMENT**
>
> This repository contains the source code for a thesis research project titled:
> *"Implementing Containerization for Deployment Portability: A Case Study of a Boarding House Management System REST API"*
>
> **This repository is intended for development and building Docker images.**
> If you only want to **deploy the application** (without building from source), use the deployment repository instead:
> 👉 [boardinghouse-api-deploy](https://github.com/ardentiena/boardinghouse-api-deploy)

---

## Daftar Isi / Table of Contents

- [Deskripsi / Description](#deskripsi--description)
- [Struktur Repository / Repository Structure](#struktur-repository--repository-structure)
- [Prerequisites](#prerequisites)
- [Cara Build / How to Build](#cara-build--how-to-build)
- [Build Docker Images](#build-docker-images)
- [Development dengan Docker Compose](#development-dengan-docker-compose--development-with-docker-compose)
- [Endpoint API](#endpoint-api--api-endpoints)
- [Konfigurasi Environment Variable](#konfigurasi-environment-variable--environment-variable-configuration)
- [Troubleshooting](#troubleshooting)
- [Deployment ke Production](#deployment-ke-production--deploying-to-production)
- [Lisensi / License](#lisensi--license)
- [Kontak / Contact](#kontak--contact)
- [Referensi / References](#referensi--references)

---

## Deskripsi / Description

REST API untuk sistem manajemen kos yang dibangun menggunakan / REST API for a boarding house management system built with:

| Teknologi / Technology | Versi / Version | Fungsi / Role |
|------------------------|-----------------|---------------|
| **Spring Boot** | 4.0.6 | Framework backend REST API |
| **Java** | 25 | Bahasa pemrograman / Programming language |
| **PostgreSQL** | 15 | Database relasional / Relational database |
| **Maven** | - | Build tool & dependency management |
| **JWT** | - | Authentication & Authorization |
| **Midtrans** | 3.2.1 | Payment gateway integration (sandbox) |
| **Docker** | - | Containerization |

---

## Struktur Repository / Repository Structure

```
boardinghouse-api-source/
├── src/
│   └── main/
│       ├── java/com/boardinghouse/boardinghouse_api/
│       │   ├── config/
│       │   │   ├── CorsConfig.java
│       │   │   ├── DataLoader.java
│       │   │   ├── MidtransConfig.java
│       │   │   └── SecurityConfig.java
│       │   ├── controller/
│       │   │   ├── AuthController.java
│       │   │   ├── HealthController.java
│       │   │   ├── PaymentController.java
│       │   │   └── RoomController.java
│       │   ├── model/
│       │   │   └── Room.java
│       │   ├── repository/
│       │   │   └── RoomRepository.java
│       │   ├── security/
│       │   │   ├── JwtAuthenticationFilter.java
│       │   │   └── JwtUtil.java
│       │   └── BoardinghouseApiApplication.java
│       └── resources/
│           └── application.properties
├── Dockerfile
├── Dockerfile.nginx
├── nginx.conf
├── pom.xml
├── docker-compose.yml       # optional, untuk development / for development
└── README.md
```

---

## Prerequisites

Untuk development / For development:

| Software | Versi / Version | Link Download |
|----------|-----------------|---------------|
| JDK | 25+ | [oracle.com/java](https://www.oracle.com/java/technologies/downloads/) |
| Maven | 3.9+ | [maven.apache.org](https://maven.apache.org/download.cgi) |
| Docker Desktop | 4.0+ | [docker.com](https://www.docker.com/products/docker-desktop) |
| PostgreSQL | 15+ | [postgresql.org](https://www.postgresql.org/download/) |
| Git | 2.0+ | [git-scm.com](https://git-scm.com/) |

---

## Cara Build / How to Build

### 1. Clone Repository

```bash
git clone https://github.com/ardentiena/boardinghouse-api-source.git
cd boardinghouse-api-source
```

### 2. Build JAR dengan Maven / Build JAR with Maven

```bash
# Linux / Mac / WSL
./mvnw clean package -DskipTests

# Windows PowerShell
.\mvnw clean package -DskipTests
```

> JAR akan dihasilkan di / JAR will be generated at: `target/boardinghouse-api-0.0.1-SNAPSHOT.jar`

### 3. Jalankan Aplikasi Tanpa Docker / Run Without Docker (For Testing)

```bash
# Set environment variables
export DB_HOST=localhost
export DB_PORT=5432
export DB_NAME=boardinghouse
export DB_USER=postgres
export DB_PASSWORD=yourpassword
export JWT_SECRET=your_jwt_secret_min_32_chars
export MIDTRANS_SERVER_KEY=your_midtrans_server_key
export MIDTRANS_CLIENT_KEY=your_midtrans_client_key
export CORS_ALLOWED_ORIGINS=http://localhost:3000
export ADMIN_USERNAME=admin
export ADMIN_PASSWORD=admin123

# Jalankan JAR / Run JAR
java -jar target/boardinghouse-api-0.0.1-SNAPSHOT.jar
```

---

## Build Docker Images

### Build API Image

```bash
docker build -t boardinghouse-api:latest .
```

### Build Nginx Image

```bash
docker build -f Dockerfile.nginx -t boardinghouse-nginx:latest .
```

### Tag untuk Docker Hub / Tag for Docker Hub

```bash
docker tag boardinghouse-api:latest ardentiena/boardinghouse-api:latest
docker tag boardinghouse-nginx:latest ardentiena/boardinghouse-nginx:latest
```

### Push ke Docker Hub / Push to Docker Hub

```bash
docker push ardentiena/boardinghouse-api:latest
docker push ardentiena/boardinghouse-nginx:latest
```

---

## Development dengan Docker Compose / Development with Docker Compose

```bash
# Buat file .env dari template / Create .env from template
cp .env.example .env

# Edit .env sesuai kebutuhan / Edit .env as needed
nano .env

# Jalankan semua container / Start all containers
docker-compose up -d
```

> **Catatan / Note:** `docker-compose.yml` di repository ini menggunakan `build:` untuk development. Untuk production, gunakan repository deployment. / The `docker-compose.yml` in this repository uses `build:` for development. For production, use the deployment repository.

---

## Endpoint API / API Endpoints

| Method | Endpoint | Deskripsi / Description | Auth |
|--------|----------|-------------------------|------|
| POST | `/api/auth/login` | Login, mendapat JWT token / Login, get JWT token | None |
| GET | `/api/rooms` | Mendapatkan semua kamar / Get all rooms | JWT |
| GET | `/api/rooms/{id}` | Mendapatkan kamar by ID / Get room by ID | JWT |
| GET | `/api/rooms/available` | Mendapatkan kamar tersedia / Get available rooms | JWT |
| POST | `/api/rooms` | Membuat kamar baru / Create new room | JWT |
| PUT | `/api/rooms/{id}` | Mengupdate kamar / Update room | JWT |
| DELETE | `/api/rooms/{id}` | Menghapus kamar / Delete room | JWT |
| POST | `/api/payments/charge` | Memproses pembayaran / Process payment | JWT |
| GET | `/health` | Health check | None |
| GET | `/api/test` | Test CORS | None |

---

## Konfigurasi Environment Variable / Environment Variable Configuration

| Variable | Wajib / Required | Default | Deskripsi / Description |
|----------|------------------|---------|-------------------------|
| `DB_HOST` | ✅ | - | Host database |
| `DB_PORT` | ❌ | `5432` | Port database |
| `DB_NAME` | ✅ | - | Nama database / Database name |
| `DB_USER` | ✅ | - | Username database |
| `DB_PASSWORD` | ✅ | - | Password database |
| `JWT_SECRET` | ✅ | - | Secret key JWT (min 32 karakter / characters) |
| `MIDTRANS_SERVER_KEY` | ✅ | - | Server key Midtrans |
| `MIDTRANS_CLIENT_KEY` | ✅ | - | Client key Midtrans |
| `MIDTRANS_IS_PRODUCTION` | ❌ | `false` | Mode produksi / Production mode (`true`/`false`) |
| `CORS_ALLOWED_ORIGINS` | ✅ | - | Origin yang diizinkan / Allowed origins (comma-separated) |
| `ADMIN_USERNAME` | ✅ | - | Username admin default |
| `ADMIN_PASSWORD` | ✅ | - | Password admin default |

### Generate JWT Secret Key

```bash
# Linux / Mac / WSL
openssl rand -base64 32

# Contoh output / Example output:
# a1b2c3d4e5f6g7h8i9j0k1l2m3n4o5p6q7r8s9t0u1v2w3x4y5z6
```

---

## Troubleshooting

### Error: Lombok Not Working di IDE / Lombok Not Working in IDE

Install plugin Lombok di IDE Anda / Install the Lombok plugin in your IDE:

- **IntelliJ IDEA:** Settings → Plugins → Search "Lombok" → Install
- **VS Code:** Install "Lombok Annotations Support" extension

### Error: `Package javax.persistence not found`

Pastikan menggunakan JDK 17+ / Make sure you are using JDK 17+. For older versions, add this dependency to `pom.xml`:

```xml
<dependency>
    <groupId>jakarta.persistence</groupId>
    <artifactId>jakarta.persistence-api</artifactId>
    <version>3.1.0</version>
</dependency>
```

### Error: Database Connection Failed Saat Testing / During Testing

Pastikan PostgreSQL berjalan dan database sudah dibuat / Make sure PostgreSQL is running and the database has been created:

```sql
CREATE DATABASE boardinghouse;
CREATE USER postgres WITH PASSWORD 'yourpassword';
GRANT ALL PRIVILEGES ON DATABASE boardinghouse TO postgres;
```

### Error: Midtrans 401 Invalid Key

1. Login ke / Login to [Midtrans Dashboard Sandbox](https://dashboard.sandbox.midtrans.com/)
2. Ambil Server Key dari / Get the Server Key from **Settings → Access Keys**
3. Pastikan menggunakan key sandbox, bukan production / Make sure you are using the sandbox key, not production

---

## Perintah Maven / Maven Commands

| Perintah / Command | Fungsi / Function |
|--------------------|-------------------|
| `./mvnw clean` | Menghapus folder target / Delete target folder |
| `./mvnw compile` | Mengcompile source code / Compile source code |
| `./mvnw test` | Menjalankan unit test / Run unit tests |
| `./mvnw package` | Membuat JAR (dengan test / with tests) |
| `./mvnw package -DskipTests` | Membuat JAR (skip test / skip tests) |
| `./mvnw install` | Install ke local repository / Install to local repository |
| `./mvnw clean package -DskipTests` | Clean + build JAR (**rekomendasi / recommended**) |

---

## Deployment ke Production / Deploying to Production

Source code ini digunakan untuk build image yang kemudian di-push ke Docker Hub. Untuk deployment, gunakan repository berikut / This source code is used to build images that are then pushed to Docker Hub. For deployment, use this repository:

👉 [boardinghouse-api-deploy](https://github.com/ardentiena/Boarding-House-API.git)

```bash
git clone https://github.com/ardentiena/Boarding-House-API.git
cd boardinghouse-api-deploy
cp .env.example .env
# Edit .env
docker-compose up -d
```

---

## Lisensi / License

Tugas Metode Penelitian — Universitas Kristen Satya Wacana (UKSW)

---

## Referensi / References

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [JWT Documentation](https://jwt.io/introduction)
- [Midtrans Documentation](https://docs.midtrans.com/)
- [Docker Documentation](https://docs.docker.com/)
- [Maven Documentation](https://maven.apache.org/guides/)
