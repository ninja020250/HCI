namespace API.Models
{
    using System.Data.Entity;

    public class _Context : DbContext
    {
        public _Context() : base("abc") { }

        public DbSet<Animal> Animals { get; set; }

        protected override void OnModelCreating(DbModelBuilder modelBuilder)
        {
            base.OnModelCreating(modelBuilder);
        }
    }
}