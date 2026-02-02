<div align="center">

# 🧠 DocuMind AI
### The Next-Gen Neural Knowledge Workspace

![Java](https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.4.2-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-pgvector-316192?style=for-the-badge&logo=postgresql&logoColor=white)
![Groq](https://img.shields.io/badge/Powered_By-Groq_Llama3-f55036?style=for-the-badge&logo=fastapi&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-Containerized-2496ED?style=for-the-badge&logo=docker&logoColor=white)

<br>

**DocuMind AI** is an advanced **RAG (Retrieval-Augmented Generation)** system that transforms static PDF documents into interactive, neural conversations. Built with a **Deep Aurora** dark aesthetic, it fuses local privacy (ONNX embeddings) with hyper-fast cloud inference (Groq Llama-3).

[View Demo](#-screenshots) • [Installation](#-installation) • [Tech Stack](#-tech-stack) • [Features](#-key-features)

</div>

---

## 🚀 Overview

DocuMind isn't just a chatbot; it's a **semantic analysis engine**. By leveraging **Vector Embeddings (pgvector)** and **Spring AI**, it understands the *context* of your documents, not just keywords. It natively supports **Hindi, English, and Hinglish**, making it the ultimate study and professional companion.

## ✨ Key Features

### 🎨 **"Deep Aurora" Interface**
* **Glassmorphism Design:** A stunning, responsive UI built with fluid clamp-based layouts.
* **Neural Animations:** Pulse loaders, typewriter effects, and dynamic gradients.
* **Adaptive Layout:** Fits perfectly on Ultra-wide monitors, Laptops, and Tablets.

### 🧠 **Cognitive Capabilities**
* **Neural Search (RAG):** Uses **ONNX MiniLM-L6-v2** (running locally) to vectorize PDFs for 100% private, free embedding generation.
* **Hyper-Fast Inference:** Powered by **Groq Cloud** (Llama-3-70B) for millisecond-latency responses.
* **Polyglot NLP:** Ask in **Hindi** ("Iska summary batao") and get accurate responses in the same language.

### 🎙️ **Interactive Tools**
* **Voice Command:** Native Speech-to-Text integration for hands-free querying.
* **Session Management:** Live session timer and chat export (`.txt`) functionality.
* **Smart Context:** Auto-remembers the uploaded document context for follow-up questions.

---

## 🛠 Tech Stack

| Component | Technology | Description |
| :--- | :--- | :--- |
| **Backend Core** | Java 21 + Spring Boot 3.4 | Robust, high-performance REST API. |
| **AI Framework** | Spring AI (0.8.1/1.0 M6) | Unified AI interaction layer. |
| **Vector DB** | PostgreSQL + `pgvector` | High-dimensional vector storage (384-dim). |
| **LLM Engine** | Groq API (Llama-3-70B) | The inference engine (Chat). |
| **Embeddings** | ONNX Runtime (Java) | **Free & Private** local vectorization. |
| **Frontend** | HTML5, CSS3, Vanilla JS | No-framework, lightweight "Deep Aurora" UI. |
| **Container** | Docker Compose | Instant database orchestration. |

---

## ⚡ Installation

### Prerequisites
* **Java 21 JDK**
* **Docker Desktop** (For the database)
* **Groq API Key** (Free at [console.groq.com](https://console.groq.com))

### 1. Clone the Repository
```bash
git clone [https://github.com/Vaishnav00521/universal-examprep-bot.git](https://github.com/Vaishnav00521/universal-examprep-bot.git)
cd universal-examprep-bot

Built with ❤️ by Vaishnav00521 using Spring AI & Groq
