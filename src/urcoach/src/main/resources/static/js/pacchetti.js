

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

