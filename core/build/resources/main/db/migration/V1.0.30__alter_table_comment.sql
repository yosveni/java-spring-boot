alter table if exists tab_comment
    add column if not exists comment_area int4 default 0;
