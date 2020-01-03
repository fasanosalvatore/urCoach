const addPacchetto = (position, pacchetto) => {
    position.innerHTML += `
                <div class="">
                    <div class="card">
                        <div class="card__img"><img src="/img/header.jpg" alt=""></div>
                        <div class="card__container">
                            <h4 class="card__title">${pacchetto.nome}</h4>
                            <ul class="card__tag">
                                <li>Durata: ${pacchetto.durata}week</li>
                                <li>Obiettivo: ${pacchetto.categoria.nome}</li>
                                <li>${pacchetto.personalTrainer.nome}</li>
                            </ul>
                            <p class="paragraph"></p>
                            <a href="/pacchetti/${pacchetto.idPacchetto}" class="button">Acquista ${pacchetto.costo}€</a>
                        </div>
                    </div>
                </div>
            `;
}

const toggle = document.querySelector(".toggle");
const nav = document.querySelector(".main-menu");
const list = document.querySelector(".menu__background");
toggle.addEventListener("click", () => {
    toggle.classList.toggle("open");
});

const name = document.querySelector("#ricercaText");
const positionRicerca = document.querySelector("#ricerca #here");
name.addEventListener("input", async e => {
    const nome = e.target.value;
    positionRicerca.innerHTML = "";
    const response = await fetch(`http://localhost:8080/api/pacchetti/${nome}`);
    const pacchetti = await response.json();
    pacchetti.map(pacchetto => addPacchetto(positionRicerca, pacchetto));
});

const registrazioneTipo = document.querySelector(".tipologia");
const registrazioneTrainer = document.querySelector("#reg_form_trainer");
const registrazioneAtleta = document.querySelector("#reg_form_atleta");

console.log(registrazioneTipo);
console.log(registrazioneTrainer);
console.log(registrazioneAtleta);

registrazioneTrainer.style.display = "none";
registrazioneAtleta.style.display = "none";

registrazioneTipo.addEventListener("change", e => {
    if(e.target.value == "PersonalTrainer") {
        registrazioneTrainer.style.display = "flex";
        registrazioneAtleta.style.display = "none";
    } else if (e.target.value == "Atleta") {
        registrazioneTrainer.style.display = "none";
        registrazioneAtleta.style.display = "flex";
    }
});