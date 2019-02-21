using API.Models;
using System;
using System.Collections.Generic;
using System.Data.Entity;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;

namespace API.Controllers
{
    public class GameController : ApiController
    {
        private readonly _Context _context = new _Context();
        public IHttpActionResult GetAll()
        {
            try
            {
                return Ok(_context.Animals);
            }
            catch (Exception e)
            {
                return InternalServerError(e);
            }
        }
        [HttpPost]
        public IHttpActionResult Create(AnimalVM vm)
        {
            try
            {
                if(ModelState.IsValid)
                {
                    using(DbContextTransaction transaction = _context.Database.BeginTransaction())
                    {
                        _context.Animals.Add(new Animal
                        {
                            ImageUrl = vm.ImageUrl,
                            Name = vm.Name,
                            AnimationUrl = vm.AnimationUrl,
                            Type = vm.Type
                        });

                        _context.SaveChanges();
                        transaction.Commit();
                    }
                }
                return Ok();
            }
            catch (Exception e)
            {
                return InternalServerError(e);
            }
        }
    }

    public class AnimalVM
    {
        public string Name { get; set; }
        public string ImageUrl { get; set; }
        public string AnimationUrl { get; set; }
        public string Type { get; set; }
    }
}
