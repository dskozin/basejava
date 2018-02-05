CREATE TABLE resume
(
  uuid      CHAR(36) NOT NULL CONSTRAINT resume_pkey PRIMARY KEY,
  full_name TEXT     NOT NULL
);

CREATE TABLE contact
(
  id          SERIAL   NOT NULL CONSTRAINT contact_pkey PRIMARY KEY,
  resume_uuid CHAR(36) NOT NULL CONSTRAINT contact_resume_uuid_fk REFERENCES resume ON DELETE CASCADE,
  type        TEXT     NOT NULL,
  value       TEXT     NOT NULL
);

CREATE TABLE public.section
(
  id SERIAL PRIMARY KEY NOT NULL,
  resume_uuid CHAR(36) NOT NULL CONSTRAINT section_resume_uuid REFERENCES resume (uuid) ON DELETE CASCADE,
  type TEXT NOT NULL,
  value TEXT NOT NULL
);

CREATE UNIQUE INDEX contact_resume_uuid_type_uindex
  ON public.contact (resume_uuid, type);

CREATE UNIQUE INDEX section_index ON public.section (type, resume_uuid);

