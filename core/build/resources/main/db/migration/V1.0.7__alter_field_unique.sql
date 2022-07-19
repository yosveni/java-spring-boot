create
unique index tab_user_cpf_uindex
	on linkapital.tab_user (cpf);

create
unique index tab_user_temp_cpf_uindex
	on linkapital.tab_user_temp (cpf);

create
unique index tab_person_cpf_uindex
	on linkapital.tab_person (cpf);