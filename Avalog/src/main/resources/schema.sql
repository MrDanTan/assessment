
DROP TABLE IF EXISTS dice_roll_simulation;

CREATE TABLE dice_roll_simulation (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  simulation_number int not null,
  number_of_dice int not null,
  number_of_dice_sides int not null,
  throw_result_sum long not null,
  throw_result clob not null
);
