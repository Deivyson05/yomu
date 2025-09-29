import React, { useState } from "react";
import "./styles.css";

export function ProfileScreen() {
  const [selectedAvatar, setSelectedAvatar] = useState(0);

  const avatars = Array.from({ length: 12 }, (_, i) => i + 1); 

  return (
    <div className="profile-container">
      <header className="profile-header">
        <button className="back-button">←</button>
        <h2>Personalize seu perfil</h2>
        <span className="step">2/2</span>
      </header>

      <div className="avatar-section">
        <div className="avatar-preview">
          {selectedAvatar ? (
            <div className="avatar-selected">{selectedAvatar}</div>
          ) : (
            <span className="avatar-placeholder">+</span>
          )}
        </div>
        <p>Escolha seu novo avatar</p>

        <div className="avatar-grid">
          {avatars.map((a) => (
            <div
              key={a}
              className={`avatar-circle ${
                selectedAvatar === a ? "selected" : ""
              }`}
              onClick={() => setSelectedAvatar(a)}
            >
              {a}
            </div>
          ))}
        </div>
      </div>

      <form className="profile-form">
        <label>Sexo:</label>
        <select>
          <option value="">Selecione</option>
          <option value="masc">Masculino</option>
          <option value="fem">Feminino</option>
          <option value="outro">Outro</option>
        </select>

        <label>Data de Nascimento:</label>
        <input type="date" />

        <label>Endereço:</label>
        <input type="text" placeholder="Rua, Número, Bairro" />

        <button type="submit" className="finalizar-btn">
          Finalizar
        </button>
      </form>
    </div>
  );
}