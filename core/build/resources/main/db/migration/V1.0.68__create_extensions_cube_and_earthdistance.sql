CREATE EXTENSION IF NOT EXISTS cube;
CREATE EXTENSION IF NOT EXISTS earthdistance;

-- select *
-- from tab_company
--          join tab_company_main_info tcmi on tcmi.id = tab_company.main_info
--          join tab_address ta on ta.id = tcmi.address_id
-- where
--     /* First condition allows to search for points at an approximate distance:
--        a distance computed using a 'box', instead of a 'circumference'.
--        This first condition will use the index.
--        (45.1013021, 46.3021011) = (lat, lng) of search center.
--        25000 = search radius (in m)
--     */
--         earth_box(ll_to_earth(-27.4449327, -48.3828056), 1000) @> ll_to_earth(ta.latitude, ta.longitude)
--
--     /* This second condition (which is slower) will "refine"
--        the previous search, to include only the points within the
--        circumference.
--     */
--   and earth_distance(ll_to_earth(-27.4449327, -48.3828056),
--                      ll_to_earth(ta.latitude, ta.longitude)) < 1000;