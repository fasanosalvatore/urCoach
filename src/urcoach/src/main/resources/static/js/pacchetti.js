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
                            <a href="PacchettoSingleServlet?id='+element.idPacchetto+'" class="button">Acquista ${pacchetto.costo}€</a>
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

const positionPacchetti = document.querySelector("#main #here");
const filtri = document.querySelector(".filtri");
const updatePacchetti = async (e) => {
    positionPacchetti.innerHTML = "";
    const response = await fetch(`http://localhost:8080/api/pacchettiFiltri?nomeTrainer=${document.querySelector('input[name=nomeTrainer]:checked').value}&categoria=${document.querySelector('input[name=obiettivo]:checked').value}&prezzo=${document.querySelector('input[name=costo]:checked').value}`);
    const pacchetti = await response.json();
    console.log(pacchetti);
    pacchetti.map(pacchetto => addPacchetto(positionPacchetti, pacchetto));
};

window.addEventListener("load", e => updatePacchetti(e));
filtri.addEventListener("change", e => updatePacchetti(e));